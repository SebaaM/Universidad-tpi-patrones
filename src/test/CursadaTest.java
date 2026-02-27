package test;

import Model.*;
import Model.EstadoState.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CursadaTest {
    
    private Cursada cursada;
    private Materia materia;
    
    @BeforeEach
    void setUp() {
        materia = new Materia("Programacion", 101, 1);
        cursada = new Cursada(materia);
    }

    @Test
    void cargarParcial() {
        // Test 1: Estado inicial debe ser Inscripto
        assertEquals("Inscripto", cursada.getEstado().getClass().getSimpleName());
        
        // Test 2: Cargar parcial con nota 6 (aprobado)
        cursada.cargarParcial(6.0);
        assertEquals("ParcialAprobado", cursada.getEstado().getClass().getSimpleName());
        assertTrue(cursada.isCursadaAprobada());
        assertFalse(cursada.isCursadaAprobadaTotal());
        
        // Test 3: Cargar parcial con nota 8 (promocion)
        Cursada cursada2 = new Cursada(materia);
        cursada2.cargarParcial(8.0);
        assertEquals("Promocionada", cursada2.getEstado().getClass().getSimpleName());
        assertTrue(cursada2.isCursadaAprobada());
        assertTrue(cursada2.isCursadaAprobadaTotal());
        
        // Test 4: Cargar parcial con nota 3 (desaprobado)
        Cursada cursada3 = new Cursada(materia);
        cursada3.cargarParcial(3.0);
        assertEquals("ParcialDesaprobado", cursada3.getEstado().getClass().getSimpleName());
        assertFalse(cursada3.isCursadaAprobada());
        assertFalse(cursada3.isCursadaAprobadaTotal());
    }

    @Test
    void cargarNotaFinal() {
        // Test 1: Intentar cargar nota final en estado Inscripto (fallo)
        cursada.cargarNotaFinal(7.0);
        assertEquals("Inscripto", cursada.getEstado().getClass().getSimpleName());
        
        // Test 2: Cargar parcial aprobado y luego nota final
        cursada.cargarParcial(6.0);
        assertEquals("ParcialAprobado", cursada.getEstado().getClass().getSimpleName());
        
        cursada.cargarNotaFinal(7.0);
        assertEquals("CursadaAprobada", cursada.getEstado().getClass().getSimpleName());
        assertTrue(cursada.isCursadaAprobada());
        assertTrue(cursada.isCursadaAprobadaTotal());
        
        // Test 3: Cargar parcial aprobado y final desaprobado
        Cursada cursada2 = new Cursada(materia);
        cursada2.cargarParcial(6.0);
        cursada2.cargarNotaFinal(3.0);
        assertEquals("CursadaDesaprobada", cursada2.getEstado().getClass().getSimpleName());
        assertFalse(cursada2.isCursadaAprobada());
        assertFalse(cursada2.isCursadaAprobadaTotal());
    }

    @Test
    void finalizarCursada() {
        // Test 1: Finalizar cursada promocionada
        cursada.cargarParcial(8.0);
        assertEquals("Promocionada", cursada.getEstado().getClass().getSimpleName());
        
        cursada.finalizarCursada();
        assertEquals("CursadaAprobada", cursada.getEstado().getClass().getSimpleName());
        assertTrue(cursada.isCursadaAprobada());
        assertTrue(cursada.isCursadaAprobadaTotal());
        
        // Test 2: Finalizar cursada con parcial desaprobado
        Cursada cursada2 = new Cursada(materia);
        cursada2.cargarParcial(3.0);
        assertEquals("ParcialDesaprobado", cursada2.getEstado().getClass().getSimpleName());
        
        cursada2.finalizarCursada();
        assertEquals("CursadaDesaprobada", cursada2.getEstado().getClass().getSimpleName());
        assertFalse(cursada2.isCursadaAprobada());
        assertFalse(cursada2.isCursadaAprobadaTotal());
    }

    @Test
    void isCursadaAprobada() {
        // Test 1: Estado inicial no aprobado
        assertFalse(cursada.isCursadaAprobada());
        
        // Test 2: Parcial aprobado
        cursada.cargarParcial(6.0);
        assertTrue(cursada.isCursadaAprobada());
        
        // Test 3: Promocionada
        Cursada cursada2 = new Cursada(materia);
        cursada2.cargarParcial(8.0);
        assertTrue(cursada2.isCursadaAprobada());
        
        // Test 4: Parcial desaprobado
        Cursada cursada3 = new Cursada(materia);
        cursada3.cargarParcial(3.0);
        assertFalse(cursada3.isCursadaAprobada());
    }

    @Test
    void isCursadaAprobadaTotal() {
        // Test 1: Estado inicial no aprobado total
        assertFalse(cursada.isCursadaAprobadaTotal());
        
        // Test 2: Parcial aprobado no es aprobado total
        cursada.cargarParcial(6.0);
        assertFalse(cursada.isCursadaAprobadaTotal());
        
        // Test 3: Promocionada es aprobado total
        Cursada cursada2 = new Cursada(materia);
        cursada2.cargarParcial(8.0);
        assertTrue(cursada2.isCursadaAprobadaTotal());
        
        // Test 4: Cursada aprobada con final
        cursada.cargarNotaFinal(7.0);
        assertTrue(cursada.isCursadaAprobadaTotal());
    }

    @Test
    void setEstado() {
        // Test 1: Cambiar estado manualmente
        EstadoCursada nuevoEstado = new Promocionada();
        cursada.setEstado(nuevoEstado);
        assertEquals("Promocionada", cursada.getEstado().getClass().getSimpleName());
        
        // Test 2: Verificar que el estado se actualiza correctamente
        cursada.setEstado(new ParcialDesaprobado());
        assertEquals("ParcialDesaprobado", cursada.getEstado().getClass().getSimpleName());
    }
}
