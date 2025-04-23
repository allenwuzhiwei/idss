package com.nusiss.idss.controller;

import com.nusiss.idss.config.ApiResponse;
import com.nusiss.idss.po.Permission;
import com.nusiss.idss.po.User;
import com.nusiss.idss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService service;

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public PermissionController(ApplicationContext context) {
        this.handlerMapping = context.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Permission>>> getAllUsers() {
        List<Permission> permissions = service.getAllPermissions();
        return ResponseEntity.ok(new ApiResponse<>(true, "Permissions retrieved successfully", permissions));
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<ApiResponse<List<Permission>>> findPermissionsByRoleId(@PathVariable Integer roleId){
        List<Permission> permissions = service.findPermissionsByRoleId(roleId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Permissions retrieved successfully", permissions));
    }

    @GetMapping("/paged")
    public ResponseEntity<Map<String, Object>> listAllApiEndpoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String name) {

        List<String> allEndpoints = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo info = entry.getKey();

            Set<PathPattern> pathPatterns = Optional.ofNullable(info.getPathPatternsCondition())
                    .map(condition -> condition.getPatterns())
                    .orElse(Collections.emptySet());

            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();

            for (PathPattern pattern : pathPatterns) {
                String path = pattern.getPatternString();
                if (methods.isEmpty()) {
                    allEndpoints.add("ALL:" + path);
                } else {
                    for (RequestMethod method : methods) {
                        allEndpoints.add(method.name() + ":" + path);
                    }
                }
            }
        }

        // Filter and paginate
        List<String> filtered = allEndpoints.stream()
                .filter(endpoint -> endpoint.toLowerCase().contains(name.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());

        int start = page * size;
        int end = Math.min(start + size, filtered.size());
        List<String> paged = (start < end) ? filtered.subList(start, end) : Collections.emptyList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", paged);
        response.put("totalElements", filtered.size());
        response.put("totalPages", (int) Math.ceil((double) filtered.size() / size));
        response.put("page", page);

        return ResponseEntity.ok(Collections.singletonMap("data", response));
    }

    @GetMapping("/all-permissions")
    public ResponseEntity<List<String>> getAllApiPermissions() {
        List<String> apiList = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();

            Set<PathPattern> patterns = Optional.ofNullable(info.getPathPatternsCondition())
                    .map(condition -> condition.getPatterns())
                    .orElse(Collections.emptySet());

            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();

            for (PathPattern pattern : patterns) {
                String path = pattern.getPatternString();
                if (methods.isEmpty()) {
                    apiList.add("ALL:" + path);
                } else {
                    for (RequestMethod method : methods) {
                        apiList.add(method.name() + ":" + path);
                    }
                }
            }
        }

        Collections.sort(apiList);
        return ResponseEntity.ok(apiList);
    }

}
