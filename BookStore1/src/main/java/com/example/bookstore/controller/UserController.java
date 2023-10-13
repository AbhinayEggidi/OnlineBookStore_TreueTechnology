package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.Review;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CartService;
import com.example.bookstore.service.ReviewService;
import com.example.bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private CartService cartService;

    @Autowired
    private ReviewService reviewService;
    
    @GetMapping("/home")
    public String homepage() {
        return "home";   
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "register";  // Assuming "register" is the name of your registration page template (register.html)
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        User registeredUser = userService.register(user);
        if (registeredUser != null) {
            // Redirect to the login page after successful registration
            return "redirect:/login";
        } else {
            return "redirect:/register?error=Registration failed. Please try again.";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Assuming "login" is the name of your login page template (login.html)
    }

//    @PostMapping("/login")
//    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
//        User user = userService.login(username, password);
//        if (user != null) {
//            model.addAttribute("successMessage", "Login successful!");
//            return "redirect:/all";
//        } else {
//            model.addAttribute("errorMessage", "Incorrect credentials. Please try again.");
//            return "login";
//        }
//       
//    }
    
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
//        User user = userService.login(username, password);
//        if (user != null) {
//            // Store the logged-in user's information in the session
//            session.setAttribute("loggedInUserId", user.getId());
//            model.addAttribute("successMessage", "Login successful!");
//            return "redirect:/all";
//        } else {
//            model.addAttribute("errorMessage", "Incorrect credentials. Please try again.");
//            return "login";
//        }
//    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.login(username, password);

        if (user != null && user.getPassword().equals(password)) {
            // Store the logged-in user's information in the session
            session.setAttribute("loggedInUserId", user.getId());
            model.addAttribute("successMessage", "Login successful!");
            return "redirect:/all";
        } else {
            model.addAttribute("errorMessage", "Incorrect credentials. Please try again.");
            return "login";
        }
    }

    
//    @GetMapping("/all")
//    public String getAllBooks(Model model) {
//        List<Book> books = bookService.getAllBooks(); // Retrieve books from your service
//        model.addAttribute("books", books);
//        return "allbooks";  // Assuming "allbooks.html" is the correct Thymeleaf template name
//    }
    
    @GetMapping("/all")
    public String getAllBooks(Model model, HttpSession session) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        // Store book information in the session
        session.setAttribute("booksInSession", books);

        return "allbooks";
    }

    
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String bookName, @RequestParam double bookPrice, HttpSession session) {
        // Get the logged-in user's ID from the session (assuming you've stored it during login)
        Long userId = (Long) session.getAttribute("loggedInUserId");

        // Check if a user is logged in
        if (userId != null) {
            // Create a cart item using the provided bookName, bookPrice, and userId
            Cart cartItem = new Cart();
            cartItem.setBookname(bookName);
            cartItem.setPrice(bookPrice);
            cartItem.setUserid(userId);

            // Add the item to the cart using the cartService
            cartService.addToCart(cartItem);
        }

        // Redirect to a page (e.g., book details page, cart page)
        return "redirect:/all";  // Replace with appropriate URL
    }
     
    @PostMapping("/carts/user")
    public String getCartByUserId(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("loggedInUserId");

        if (userId != null) {
            List<Cart> carts = cartService.getCartByUserId(userId);
            model.addAttribute("carts", carts);
            model.addAttribute("userid", userId);
            return "cart";
        } else {
            // Handle the case where the user is not logged in
            return "redirect:/login";
        }
    }
    @GetMapping("/carts/user/{userId}")
    public String viewUserCart(@PathVariable Long userId, Model model) {
        // Get cart details for the user with userId and add it to the model
        List<Cart> carts = cartService.getCartByUserId(userId);
        model.addAttribute("carts", carts);
        model.addAttribute("userid", userId);
        
        // Return the view for displaying the cart details
        return "cart"; // Adjust the view name based on your configuration
    }


    @PostMapping("/delete/{cartId}")
    public String removeFromCart(@PathVariable Long cartId, Model model, HttpSession session) {
        // Assume cartService has a method to remove the item from the cart
        cartService.removeCartItem(cartId);

        // Get the logged-in user's ID
        Long userId = (Long) session.getAttribute("loggedInUserId");

        // Redirect to the cart page for the specific user
        return "redirect:/carts/user/" + userId;
    }
    
//    @GetMapping("/byBook/{bookId}")
//    public ResponseEntity<List<Review>> getReviewsByBookId(@PathVariable Long bookId) {
//        List<Review> reviews = reviewService.findReviewsByBookid(bookId);
//        return ResponseEntity.ok(reviews);
//    }
    
    
//    @GetMapping("/showReviews/{bookId}")
//    public String getReviewsByBookId(@PathVariable(value = "bookId") String bookIdStr, Model model) {
//        try {
//            Long bookId = Long.parseLong(bookIdStr);
//            List<Review> reviews = reviewService.findReviewsByBookid(bookId);
//            model.addAttribute("reviews", reviews);
//            model.addAttribute("bookId", bookId);
//            return "reviews"; // Thymeleaf template name (reviews.html)
//        } catch (NumberFormatException e) {
//            // Handle the case where the bookId is not a valid Long
//            return "error"; // Thymeleaf template for error handling
//        }
//    }
    @GetMapping("/showReviews/{bookId}")
    public String getReviewsByBookId(@PathVariable(value = "bookId") String bookIdStr, Model model) {
        try {
            Long bookId = Long.parseLong(bookIdStr);
            List<Review> reviews = reviewService.findReviewsByBookid(bookId);
            model.addAttribute("reviews", reviews);
            model.addAttribute("bookId", bookId);
            return "reviews"; // Thymeleaf template name (reviews.html)
        } catch (NumberFormatException e) {
            // Handle the case where the bookId is not a valid Long
            return "error"; // Thymeleaf template for error handling
        }
    }

    @GetMapping("/add")
    public String addReview(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);  // Add bookId to the model
        return "addreview"; // Return the view name
    }

    
//    @PostMapping("/add")
//    public String addReview(
//            @RequestParam("review") String review,
//            @RequestParam("bookId") Long bookId) {
//        reviewService.addReview(review, bookId);
//        return "redirect:/showReviews?bookId=" + bookId;
//    }
    @PostMapping("/add/{bookId}")
    public String addReview(
            @RequestParam("review") String review,
            @PathVariable("bookId") Long bookId) {
        // Add review for the given bookId
        reviewService.addReview(review, bookId);
        
        // Redirect to the page showing reviews for the specific book
        return "redirect:/showReviews/{bookId}";
    }



    
}
