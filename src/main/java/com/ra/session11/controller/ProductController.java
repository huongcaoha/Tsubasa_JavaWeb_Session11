package com.ra.session11.controller;

import com.ra.session11.model.dto.ProductDTO;
import com.ra.session11.model.entity.Product;
import com.ra.session11.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model, HttpServletRequest request) {
        String isLogin = checkLogin(request);
        if (isLogin != null) return isLogin;

        model.addAttribute("products", productService.findAll());
        return "/product/productList"; // Tên của trang JSP để hiển thị danh sách sản phẩm
    }

    private static String checkLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String username = null ;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        if (username == null) {
            return "redirect:/auth/login";
        }
        return null;
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model,HttpServletRequest request) {
        String isLogin = checkLogin(request);
        if (isLogin != null) return isLogin;

        model.addAttribute("product", new ProductDTO());
        return "/product/addProduct"; // Trả về view addProduct.jsp
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Model model,HttpServletRequest request) {
        String isLogin = checkLogin(request);
        if (isLogin != null) return isLogin;

        if (productDTO.getImage() == null || productDTO.getImage().isEmpty()) {
            bindingResult.rejectValue("image", "error.image.empty", "image is empty");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDTO);
            return "/product/addProduct";
        }

        Product product = productService.save(productDTO);
        if (product != null) {
            redirectAttributes.addFlashAttribute("message", "Product added successfully");
            return "redirect:/products";
        }else {
            model.addAttribute("message", "Product added failed");
            model.addAttribute("product", productDTO);
            return "/product/addProduct";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model,HttpServletRequest request) {
        String isLogin = checkLogin(request);
        if (isLogin != null) return isLogin;
        // Lấy thông tin sản phẩm theo id
        Product product = productService.findById(id);
        ProductDTO productDTO = productService.convertProductToProductDTO(product);
        model.addAttribute("product", productDTO);
        model.addAttribute("image", product.getImage());
        model.addAttribute("id",id);
        return "/product/editProduct"; // Trả về view editProduct.jsp
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                               @Valid @ModelAttribute("product") ProductDTO productDTO , BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,Model model ) {
        // Lấy sản phẩm cũ từ cơ sở dữ liệu
        Product product = productService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDTO);
            model.addAttribute("image", product.getImage());
            model.addAttribute("id",id);
            return "/product/editProduct";
        }

        Product updatedProduct = productService.update(productDTO,id);
        if (updatedProduct != null) {
            redirectAttributes.addFlashAttribute("message", "Product updated successfully");
            return "redirect:/products";
        }else {
            redirectAttributes.addFlashAttribute("message", "Product updated failed");
            model.addAttribute("product", productDTO);
            return "/product/editProduct";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {
        // Kiểm tra quyền truy cập: chỉ cho phép người dùng đã đăng nhập
        String isLogin = checkLogin(request);
        if (isLogin != null) return isLogin;
        Product product = productService.findById(id);

        // Xóa sản phẩm khỏi cơ sở dữ liệu
       boolean rs = productService.deleteById(id);
        if (rs){
            List<Product> products = new ArrayList<>();
            List<Product> deletedProducts = (List<Product>) session.getAttribute("deletedProducts");
            if (deletedProducts != null) {
                products = deletedProducts;
            }
            products.add(product);
            session.setAttribute("deletedProducts", products);
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
        }else {
            redirectAttributes.addFlashAttribute("message", "Product deleted failed");
        }

        return "redirect:/products"; // Chuyển hướng về danh sách sản phẩm
    }

    @GetMapping("/listProductDelete")
    public String listDeletedProducts(HttpSession session, Model model) {

        List<Product> deletedProducts = (List<Product>) session.getAttribute("deletedProducts");
        model.addAttribute("deletedProducts", deletedProducts != null ? deletedProducts : new ArrayList<>());
        return "/product/listProductDelete"; // Trả về view listProductDelete.jsp
    }
}