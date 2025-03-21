# google-groups-checker-service-java

# Prerequisites:

1. Set your Customer/Client ID in the applications Properties file or as an environment variable
   1. You can get your client ID from the Admin Console with [these steps](https://support.google.com/cloudidentity/answer/10070793)
     ```
     google.customer.id=CABCDEFG
     ```

1. Add A Service account to the Groups You want it to have access to as a Manager or Owner.
   An Alternative is to add the Service account to the build in Groups Reader Role within the Admin
   console.Service accounts cannot be added directly, so add the service account to another Google
   Group and give that google group the Role. 

# Examples(Using HTTPie):

Get a List of Groups Your Account has access to or is a Manager of:
```
$ http :8082/groups

HTTP/1.1 200 
Connection: keep-alive
Content-Length: 1112
Content-Type: text/plain;charset=UTF-8
Keep-Alive: timeout=60

{
    "groups": [
        {
            "displayName": "admins",
            "groupKey": {
                "id": "admins@example.com"
            },
            "name": "groups/03sfawer"
        },
        {
            "displayName": "developers",
            "groupKey": {
                "id": "developers@example.com"
            },
            "name": "groups/01cp5o7u"
        }
    ]
}
```

Get Members of a Group where `groupId` os the value received from the /Groups endpoint of the desired group:

```
$ http :8082/memberships?groupId=01cp5o7u

HTTP/1.1 200 
Connection: keep-alive
Content-Length: 780
Content-Type: text/plain;charset=UTF-8
Keep-Alive: timeout=60

{
    "memberships": [
        {
            "name": "groups/01cp5o7u/memberships/11859459328398288",
            "preferredMemberKey": {
                "id": "jayson@example.com"
            },
            "roles": [
                {
                    "name": "OWNER"
                },
                {
                    "name": "MEMBER"
                }
            ]
        },
        {
            "name": "groups/01cp5o7u/memberships/11709930275849027",
            "preferredMemberKey": {
                "id": "application-sa@gcp-project.gserviceaccount.com"
            },
            "roles": [
                {
                    "name": "MEMBER"
                }
            ]
        }
    ]
}
```