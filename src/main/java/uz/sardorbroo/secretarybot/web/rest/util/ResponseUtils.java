package uz.sardorbroo.secretarybot.web.rest.util;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Optional;

public class ResponseUtils {

    public static <T> ResponseEntity<T> wrap(Optional<T> optional) {

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static <T> ResponseEntity<Collection<T>> wrap(Collection<T> collection, boolean errorIfEmpty) {

        if (errorIfEmpty) {

            if (CollectionUtils.isEmpty(collection)) return ResponseEntity.notFound().build();
        } else {

            if (CollectionUtils.isEmpty(collection)) return ResponseEntity.ok(collection);
        }

        return ResponseEntity.ok(collection);
    }
}
