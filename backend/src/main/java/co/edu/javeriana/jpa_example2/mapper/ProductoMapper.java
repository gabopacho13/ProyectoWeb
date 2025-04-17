package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.ProductoDTO;
import co.edu.javeriana.jpa_example2.model.Producto;

public class ProductoMapper {
    public static ProductoDTO toDTO(Producto producto){
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        
        return productoDTO;
    }
    
    public static Producto toEntity(ProductoDTO productoDTO){
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        
        return producto;
    }
}
