package com.demo.workspace.directory.Controllers;

import com.demo.workspace.directory.Utils.GoogleAPIWrapper;
import com.demo.workspace.directory.Utils.ServiceAccountUtils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GoogleGroupsRestController {
    private static final Logger logger = LoggerFactory.getLogger(GoogleGroupsRestController.class);

    private final ServiceAccountUtils serviceAccountUtils;
    @Value("${google.customer.id}")
    private String customerId;
    private static final String BASE_URL = "https://cloudidentity.googleapis.com/v1/groups";

    public GoogleGroupsRestController(ServiceAccountUtils serviceAccountUtils) {
        this.serviceAccountUtils = serviceAccountUtils;
        logger.info("ServiceAccountUtils bean injected into GoogleGroupsRestController");
    }

    // List All Groups: GET https://cloudidentity.googleapis.com/v1/groups
    // Docs: https://cloud.google.com/identity/docs/reference/rest/v1/groups/list
    @GetMapping("/groups")
    public String listGroups() throws IOException {
        String url = BASE_URL + "?parent=customers/" + customerId;
        return GoogleAPIWrapper.makeHttpRequest(serviceAccountUtils.getAccessTokenValue(), url);
    }

    // List Memberships of a Google Group: GET https://cloudidentity.googleapis.com/v1/groups/${groupId}/memberships
    @GetMapping("/memberships")
    public String listMemberships(
            @RequestParam(name = "groupId") String groupId
    ) throws IOException {
        String url = BASE_URL + "/" + groupId + "/memberships";
        return GoogleAPIWrapper.makeHttpRequest(serviceAccountUtils.getAccessTokenValue(), url);
    }
}
