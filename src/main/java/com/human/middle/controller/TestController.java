package com.human.middle.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // JSON 역직렬화 기능이 포함되어 있음
@RequestMapping("/api")  // 클래스별 공통 URL(prefix)
public class TestController {
    // DB 대신에 List 사용
    private static final List<Map<String, Object>> productList = new ArrayList<>();
    private static int nextId = 1;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    // 매개변수가 없은 Get 메서드 구현
    @GetMapping("/name")
    public String getName() {
        return "휴먼교육센터";
    }
    // PathVariable : URL 경로에 값을 포함하여 요청을 처리
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable String id) {
        return id;
    }
    // RequestParam : 쿼리 형식으로 값을 전달
    // http://localhost:8111/api/request?name=곰돌이&email=jks2024@gmail.com&phone=010-5006-4146
    @GetMapping("/request")
    public String getRequestParam(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone
    ) {
        return name + " " + email + " " + phone;
    }

    // POST 방식 : Body에 정보를 실어서 전송 하는 방식
    // email, pwd, name 전송 해서 수신 받기
    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> map) {
        Map<String, String> result = new HashMap<>();
        result.put("name", map.get("name"));
        result.put("email", map.get("email"));
        result.put("pwd", map.get("pwd"));
        return result.toString();
    }

    // POST 방식
    // email, pwd를 입력해서 로그인 성공 실패를 전달하기
    // 반환값은 boolean 타입으로 성공/실패
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        String pwd = map.get("pwd");
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "로그인 성공");
        return result;
    }


    // POST 방식으로 상품 등록 하기
    // id(자동증가), name, price, category, desc
    @PostMapping("/product")
    public Map<String, Object> createProduct(@RequestBody Map<String, String> map) {
        // 등록할 상품 구성
        Map<String, Object> product = new HashMap<>();
        product.put("id", nextId++);
        product.put("name", map.get("name"));
        product.put("price", map.get("price"));
        product.put("category", map.get("category"));
        product.put("desc", map.get("desc"));

        productList.add(product);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "상품 등록 완료");
        result.put("product", product);
        return result;
    }

    // GET으로 상품 조회 하기
    @GetMapping("/product")
    public List<Map<String, Object>> getProductList() {
        return productList;
    }

    // PUT으로 상품 수정하기
    @PutMapping("/product/{id}")
    public Map<String, Object> updateProduct(@PathVariable String id, @RequestBody Map<String, String> map) {
        Map<String, Object> product = new HashMap<>();
        product.put("id", id);
        product.put("name", map.get("name"));
        product.put("price", map.get("price"));
        product.put("category", map.get("category"));
        product.put("desc", map.get("desc"));

        Map<String, Object> result = new HashMap<>();
        result.put("message", id + "번 상품 수정 완료");
        result.put("product", product);
        return result;
    }

    // DELETE로 상품 삭제 하기
    @DeleteMapping("/product/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id) {
        boolean success = productList.removeIf(product -> product.get("id").equals(id));

        Map<String, Object> result = new HashMap<>();
        result.put("message", success ? id + "번 상품 삭제 완료" : id + "번 상품을 찾을 수 없습니다");
        return result;
    }

}
