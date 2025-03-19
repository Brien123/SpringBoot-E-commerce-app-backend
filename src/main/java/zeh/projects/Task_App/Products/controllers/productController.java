package zeh.projects.Task_App.Products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zeh.projects.Task_App.Products.DTO.productCreateDTO;
import zeh.projects.Task_App.Products.DTO.productResponseDTO;
import zeh.projects.Task_App.Products.DTO.productUpdateDTO;
import zeh.projects.Task_App.Products.service.productService;
import zeh.projects.Task_App.users.models.User;
import zeh.projects.Task_App.users.DAO.userDAO;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class productController {

    @Autowired
    private userDAO userDAO;

    @Autowired
    private productService productService;

    @PostMapping("/create")
    public ResponseEntity<productResponseDTO> create(productCreateDTO productCreateDTO) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDAO.findByUsername(username);
        return ResponseEntity.ok(productService.create(productCreateDTO, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<productResponseDTO> get(@PathVariable long id){
        return ResponseEntity.ok(productService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<productResponseDTO> update(@RequestBody productUpdateDTO productUpdateDTO, @PathVariable long id) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDAO.findByUsername(username);
        return ResponseEntity.ok(productService.update(productUpdateDTO, id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(productService.delete(id, username));
    }

    @GetMapping("/")
    public ResponseEntity<List<productResponseDTO>> getAllProducts(@RequestParam(defaultValue = "1") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDAO.findByUsername(username);
        return ResponseEntity.ok(productService.getAllProducts(user, page));
    }

}
