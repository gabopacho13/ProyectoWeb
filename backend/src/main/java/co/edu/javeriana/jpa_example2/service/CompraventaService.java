package co.edu.javeriana.jpa_example2.service;

import co.edu.javeriana.jpa_example2.model.*;
import co.edu.javeriana.jpa_example2.repository.InventarioCaravanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompraventaService {

    @Autowired
    private InventarioCaravanaRepository inventarioCaravanaRepository;

    public double calcularPrecioCompra(ProductoCiudad productoCiudad) {
        double stock = productoCiudad.getStock();
        double factorOferta = productoCiudad.getFactor_oferta();
        return factorOferta / (1.0 + stock);
    }

    public double calcularPrecioVenta(ProductoCiudad productoCiudad) {
        double stock = productoCiudad.getStock();
        double factorDemanda = productoCiudad.getFactor_demanda();
        return factorDemanda / (1.0 + stock);
    }

    public boolean puedeComprar(Caravana caravana, ProductoCiudad productoCiudad, int cantidad) {
        double precioCompra = calcularPrecioCompra(productoCiudad);
        double costoTotal = cantidad * precioCompra;
        return costoTotal <= caravana.getDinero();
    }

    public void comprar(Caravana caravana, Ciudad ciudad, String nombreProducto, int cantidad) {
        Optional<ProductoCiudad> pcOpt = ciudad.getProductoCiudades().stream()
                .filter(pc -> pc.getProducto().getNombre().equalsIgnoreCase(nombreProducto))
                .findFirst();

        if (pcOpt.isEmpty()) throw new IllegalArgumentException("Producto no disponible en ciudad");

        ProductoCiudad productoCiudad = pcOpt.get();
        if (!puedeComprar(caravana, productoCiudad, cantidad))
            throw new IllegalArgumentException("No se puede comprar: dinero insuficiente");

        double precioUnitario = calcularPrecioCompra(productoCiudad);
        double total = cantidad * precioUnitario;

        caravana.setDinero((int)(caravana.getDinero() - total));
        productoCiudad.setStock(productoCiudad.getStock() - cantidad);

        Optional<InventarioCaravana> invOpt = caravana.getInventarioCaravanas().stream()
            .filter(ic -> ic.getProducto().getNombre().equalsIgnoreCase(nombreProducto))
            .findFirst();

        if (invOpt.isPresent()) {
            InventarioCaravana inv = invOpt.get();
            inv.setCantidad(inv.getCantidad() + cantidad);
            inventarioCaravanaRepository.save(inv);
        } else {
            InventarioCaravana nuevo = new InventarioCaravana(cantidad, caravana, productoCiudad.getProducto());
            inventarioCaravanaRepository.save(nuevo);
            caravana.getInventarioCaravanas().add(nuevo);
        }
    }

    public void vender(Caravana caravana, Ciudad ciudad, String nombreProducto, int cantidad) {
        Optional<ProductoCiudad> pcOpt = ciudad.getProductoCiudades().stream()
                .filter(pc -> pc.getProducto().getNombre().equalsIgnoreCase(nombreProducto))
                .findFirst();

        if (pcOpt.isEmpty()) throw new IllegalArgumentException("Producto no disponible en ciudad");

        ProductoCiudad productoCiudad = pcOpt.get();

        Optional<InventarioCaravana> invOpt = caravana.getInventarioCaravanas().stream()
            .filter(ic -> ic.getProducto().getNombre().equalsIgnoreCase(nombreProducto))
            .findFirst();

        if (invOpt.isEmpty() || invOpt.get().getCantidad() < cantidad) {
            throw new IllegalArgumentException("La caravana no tiene suficiente cantidad del producto para vender");
        }

        double precioUnitario = calcularPrecioVenta(productoCiudad);
        double total = cantidad * precioUnitario;

        caravana.setDinero((int)(caravana.getDinero() + total));
        productoCiudad.setStock(productoCiudad.getStock() + cantidad);

        InventarioCaravana inventario = invOpt.get();
        inventario.setCantidad(inventario.getCantidad() - cantidad);
        inventarioCaravanaRepository.save(inventario);
    }
}
