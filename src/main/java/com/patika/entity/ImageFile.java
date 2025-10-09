package com.patika.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String name;
    private String type;
    private Long length;

    public ImageFile(String name, String type, Long length, byte[] data) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.data = data;
    }

    @Lob
    private byte[] data;




}
