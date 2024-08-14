package tech.suji.seven_prods.projects.mycart.service;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import tech.suji.seven_prods.model.PaginationResponse;
import tech.suji.seven_prods.projects.mycart.dto.ProductDTO;
import tech.suji.seven_prods.projects.mycart.entity.Product;
import tech.suji.seven_prods.projects.mycart.repo.CartRepository;
import tech.suji.seven_prods.projects.mycart.repo.ProductRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
@Transactional
@CrossOrigin("*")
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public ProductService(final ProductRepository productRepository,
            final CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public PaginationResponse<ProductDTO> findAll(int pageNumber, int pageSize) {
    	// Pageable pageable = PageRequest.of(pageNumber, pageSize).withSort(Sort.by("id").descending());
    	Pageable pageable = PageRequest.of(pageNumber, pageSize);
    	
    	Page<Product> page = productRepository.findAll(pageable);
        
    	PaginationResponse<ProductDTO> pageRes = new PaginationResponse<>();
    	System.out.println(page);
         List<ProductDTO> modifiedList = page.getContent().stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
         
         pageRes.setContent(modifiedList);
         pageRes.setNumber(page.getNumber());
         pageRes.setSize(page.getSize());
         pageRes.setTotalPages(page.getTotalPages());
         return pageRes;
    }

    public ProductDTO get(final Long id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public void update(final Long id, final ProductDTO productDTO) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        cartRepository.findAllByProduct(product)
                .forEach(cart -> cart.getProduct().remove(product));
        productRepository.delete(product);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setRating(product.getRating());
        productDTO.setCategory(product.getCategory());
        productDTO.setStock(product.getStock());
        productDTO.setReleaseDate(product.getReleaseDate());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setRating(productDTO.getRating());
        product.setCategory(productDTO.getCategory());
        product.setReleaseDate(productDTO.getReleaseDate());
        product.setStock(productDTO.getStock());
        return product;
    }

}
