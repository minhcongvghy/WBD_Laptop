package com.codegym.wbdlaptop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String name;

    @Lob
    private String cpu;

    private String ram;

    private Double price;

    @Lob
    private String description;

    @Lob
    private String urlFile;

    @Lob
    private String blobString;

    private Boolean isUpdate;

    @ManyToOne
    private Line line;

    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = Comment.class, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Product() {
    }

    public Product(String name, String cpu, String ram, Double price, String description, String urlFile, String blobString, Boolean isUpdate, Line line, User user, List<Comment> comments) {
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.price = price;
        this.description = description;
        this.urlFile = urlFile;
        this.blobString = blobString;
        this.isUpdate = isUpdate;
        this.line = line;
        this.user = user;
        this.comments = comments;
    }

    public Product(String name, String cpu, String ram, Double price, String description, String urlFile, String blobString, Boolean isUpdate, Line line, User user) {
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.price = price;
        this.description = description;
        this.urlFile = urlFile;
        this.blobString = blobString;
        this.isUpdate = isUpdate;
        this.line = line;
        this.user = user;
    }

    public Product(String name, String cpu, String ram, Double price, String description, String urlFile) {
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.price = price;
        this.description = description;
        this.urlFile = urlFile;
    }

    public Product(String name, String cpu, String ram, Double price, String description) {
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getBlobString() {
        return blobString;
    }

    public void setBlobString(String blobString) {
        this.blobString = blobString;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
