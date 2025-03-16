package zeh.projects.Task_App.Products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zeh.projects.Task_App.Products.DTO.productCreateDTO;
import zeh.projects.Task_App.Products.DTO.productResponseDTO;
import zeh.projects.Task_App.Products.service.productService;

import java.io.IOException;

@Controller
@RequestMapping("/products")
public class productcontroller {

    @Autowired
    private productService productService;

    @PostMapping("/")
    public ResponseEntity<productResponseDTO> create(productCreateDTO productCreateDTO) throws IOException {
        return ResponseEntity.ok(productService.create(productCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<productResponseDTO> get(@PathVariable long id){
        return ResponseEntity.ok(productService.get(id));
    }

}
