package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;
import co.edu.javeriana.jpa_example2.model.ProductoCiudad;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.ProductoService;

public class ProductoCiudadMapper {
    // Convierte de entidad a DTO
    public static ProductoCiudadDTO toDTO(ProductoCiudad productoCiudad) {
        if (productoCiudad == null) {
            return null;
        }
        return new ProductoCiudadDTO(
            productoCiudad.getId(),
            productoCiudad.getProducto().getId(),
            productoCiudad.getCiudad().getId(),
            productoCiudad.getFactor_demanda(),
            productoCiudad.getFactor_oferta(),
            productoCiudad.getStock()
        );
    }

    // Convierte de DTO a entidad
    public static ProductoCiudad toEntity(ProductoCiudadDTO productoCiudadDTO, ProductoService productoService, CiudadService ciudadService) {
        if (productoCiudadDTO == null) {
            return null;
        }
        ProductoCiudad productoCiudad = new ProductoCiudad();
        productoCiudad.setId(productoCiudadDTO.getId());
        productoCiudad.setProducto(productoService.buscarProducto(productoCiudadDTO.getProductoId()).map(ProductoMapper::toEntity).orElse(null));
        productoCiudad.setCiudad(ciudadService.buscarCiudad(productoCiudadDTO.getCiudadId()).map(CiudadMapper::toEntity).orElse(null));
        productoCiudad.setFactor_demanda(productoCiudadDTO.getFactorDemanda());
        productoCiudad.setFactor_oferta(productoCiudadDTO.getFactorOferta());
        productoCiudad.setStock(productoCiudadDTO.getStock());
        return productoCiudad;
    }
}