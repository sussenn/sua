package com.itc.sua.system;

import com.itc.sua.common.pojo.auth.Menu;
import com.itc.sua.system.service.SysMenuService;
import com.itc.sua.system.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName AuthTest
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/22
 */
@SpringBootTest(classes = SystemMain.class)
public class AuthHeaderTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private SysMenuService menuService;

    @Test
    public void test0010() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJhZG1pbiJ9.EXtCAorqevZVU2dLrlFA0fjiiJ28U2zaooFBHj6xOvc";
        String s = JwtUtil.getClaims(token).getSubject();
        System.err.println(s);
    }


    @Test
    public void test009() {
        List<Menu> menus = menuService.getUserMenus(2L);
        ArrayList<String> pathList = new ArrayList<>();
        getPath(pathList, menus);
        pathList.forEach(System.err::println);

    }

    private void getPath(List<String> pathList, List<Menu> menus) {
        for (Menu menu : menus) {
            if (menu.getMenuType().equals("F")) {
                pathList.add(menu.getPath());
            }
            List<Menu> childes = menu.getChild();
            getPath(pathList, childes);
        }
    }

    @Test
    public void test008() {
        // 查询出用户菜单权限
        List<Menu> menus = menuService.getUserMenus(1L);
        // 获取用户拥有菜单的path, 放入redis
        List<String> pathList = menus.stream()
                .flatMap(menu -> menu.getChild().stream()
                        .flatMap(me -> me.getChild().stream()))
                .filter(menu -> "F".equals(menu.getMenuType()))
                .map(Menu::getPath)
                .collect(Collectors.toList());

        pathList.forEach(System.err::println);
    }

    @Test
    public void test007() {
        String path = "/sys/user/del/2/1";
        List<String> pathList = Arrays.asList("/sys/user/add", "/sys/user/list");
        boolean hasPath = pathList.stream().anyMatch(path::startsWith);
        System.err.println(hasPath);
    }

    @Test
    public void test006() {
        String uri = "/sys/user/getUser/1";
        // 配置gateway路径了的, 才需要截取/gateway
        //uri = StrUtil.subSuf(uri, 7);
        //uri = StrUtil.subSuf(uri, uri.indexOf("/", 1));

        System.err.println(uri);

        boolean b = uri.startsWith("/sys/user/getUser");
        System.err.println(b);
    }


    @Test
    public void test001() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("1234");
        System.err.println(encode);
    }

}
