package ru.sfu.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.sfu.entity.Television;

import java.util.List;
import java.util.Objects;

/**
 * REST Client Example
 * @author Agapchenko V.V.
 */
public class RestClient {
    /**
     * Host URL
     */
    static String URL = "http://localhost:8080";
    /**
     * Rest Template Object
     */
    static RestTemplate rest = new RestTemplate();

    /**
     * REST Template Usage Example
     * @param args Ignored
     */
    public static void main(String[] args) {
        String producer = "otherProducer";
        int width = 1366;
        int height = 768;
        int id = 5;

        System.out.println("> GET Televisions");

        getTelevisions().forEach(System.out::println);

        System.out.printf("> GET Television #%d\n", id);

        Television tv = getTelevision(id);
        System.out.println(tv);

        System.out.printf("> PUT & GET Television #%d\n", id);

        tv.setProducer(producer);

        putTelevision(tv);

        System.out.println(getTelevision(id));

        System.out.printf("> POST Television #%d\n", ++id);

        tv.setId(id);
        tv.setWidth(width);
        tv.setHeight(height);

        ResponseEntity<Television> response = postTelevision(tv);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Location: " + Objects.requireNonNull(
                response.getHeaders().getLocation()).getPath()
        );

        System.out.printf("> GET Television #%d\n", id);
        System.out.println(getTelevision(id));

        System.out.printf("> DELETE Television #%d\n", id);
        deleteTelevision(id);

        System.out.printf(
                "> GET Televisions with width='%d' & height='%d'\n",
                width,
                height
        );
        getByWidthAndHeight(width, height).forEach(System.out::println);
    }

    /**
     * GET Televisions with REST Template
     * @return List of Televisions
     */
    public static List<Television> getTelevisions() {
        return List.of(
                Objects.requireNonNull(
                        rest.getForObject(URL + "/tvs", Television[].class
                        )
                )
        );
    }

    /**
     * GET Television with REST Template
     * @param id Identification number
     * @return Television
     */
    public static Television getTelevision(int id) {
        return rest.getForObject(URL + "/tvs/{id}", Television.class, id);
    }

    /**
     * POST Television with REST Template
     * @param tv Television
     * @return Response Object
     */
    public static ResponseEntity<Television> postTelevision(Television tv) {
        return rest.postForEntity(
                URL + "/tvs",
                tv,
                Television.class
        );
    }

    /**
     * PUT Television with REST Template
     * @param tv Television
     */
    public static void putTelevision(Television tv) {
        rest.put(URL + "/tvs/{id}", tv, tv.getId());
    }

    /**
     * DELETE Television with REST Template
     * @param id Identification number
     */
    public static void deleteTelevision(int id) {
        rest.delete(URL + "/tvs/{id}", id);
    }

    /**
     * GET Televisions by Width and Weight with REST Template
     * @param width Width
     * @param height Height
     * @return List of Televisions
     */
    public static List<Television> getByWidthAndHeight(int width, int height) {
        return List.of(
                Objects.requireNonNull(
                        rest.getForObject(
                                URL + "/tvs/{width}/{height}",
                                Television[].class,
                                width,
                                height
                        )
                )
        );
    }
}
