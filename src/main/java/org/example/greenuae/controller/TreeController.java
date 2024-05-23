package org.example.greenuae.controller;

import org.example.greenuae.model.Tree;
import org.example.greenuae.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class TreeController {
    private TreeService treeService;

    @Autowired
    public TreeController(TreeService treeService) {
        super();
        this.treeService = treeService;
    }

    @GetMapping(value = "/tree")
    public String signUp() {
        return "tree";
    }


    @PostMapping(value = "/addTree")
    public ResponseEntity<?> addTree(
            @RequestParam("tree_photo") MultipartFile treePhoto,
            @RequestParam("gps_location") String gpsLocation,
            @RequestParam("points_rewarded") int pointsRewarded,
            @RequestParam("planting_date") String plantingDate,
            @RequestParam("user_id") long user_id) {

        try {
            byte[] photoBytes = treePhoto.getBytes();
            Tree tree = new Tree();
            tree.setTree_photo(photoBytes);
            tree.setGps_location(gpsLocation);
            tree.setPoints_rewarded(pointsRewarded);
            tree.setPlanting_date(plantingDate);
            tree.setUser_id(user_id);

            Tree savedTree = treeService.addTree(tree);
            return new ResponseEntity<>(savedTree, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing tree photo", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error adding tree: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getTree/{id}")
    public ResponseEntity<Tree> getTree(@PathVariable("id") long treeId) {
        return new ResponseEntity<Tree>(treeService.getTreeById(treeId), HttpStatus.OK);
    }

    @GetMapping(value = "/trees")
    public List<Tree> getTrees() {
        return treeService.getAllTree();
    }

    @PutMapping(value = "/updateTree/{id}")
    public ResponseEntity<?> updateTree(@PathVariable("id") long treeId,
                                        @RequestParam("tree_photo") MultipartFile treePhoto,
                                        @RequestParam("gps_location") String gpsLocation,
                                        @RequestParam("points_rewarded") int pointsRewarded,
                                        @RequestParam("planting_date") String plantingDate) {
        try {
            byte[] photoBytes = treePhoto.getBytes();

            Tree updatedTree = new Tree();
            updatedTree.setTree_id(treeId);
            updatedTree.setTree_photo(photoBytes);
            updatedTree.setGps_location(gpsLocation);
            updatedTree.setPoints_rewarded(pointsRewarded);
            updatedTree.setPlanting_date(plantingDate);

            Tree updated = treeService.updateTree(updatedTree, treeId);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing tree photo");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating tree: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteTree/{id}")
    public ResponseEntity<String> deleteTree(@PathVariable("id") long treeId) {
        treeService.deleteTree(treeId);
        return new ResponseEntity<String>("Tree deleted!", HttpStatus.OK);
    }
}