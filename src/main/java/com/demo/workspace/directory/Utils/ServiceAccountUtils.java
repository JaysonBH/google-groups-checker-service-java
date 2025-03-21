package com.demo.workspace.directory.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;


public class ServiceAccountUtils {
	private static final String CLOUD_IDENTITY_GROUPS = "https://www.googleapis.com/auth/cloud-identity.groups";
	private static final List<String> SCOPES = new ArrayList<>(Arrays.asList(CLOUD_IDENTITY_GROUPS));

	private ServiceAccountCredentials credentials;

	public ServiceAccountCredentials getCredentials() {
		return this.credentials;
	}

	public String getServiceAccountEmail() {
		return this.credentials.getAccount();
	}

	public AccessToken getAccessToken() {
		return this.credentials.getAccessToken();
	}

	public String getAccessTokenValue() {
		AccessToken accessToken = this.credentials.getAccessToken();
		return accessToken.getTokenValue();
	}

	public void refreshAccessToken() throws IOException {
		this.credentials.refresh();
	}

	public ServiceAccountUtils() throws IOException {
		// This method uses Google Application Default Credentials to obtain a Credential object.
		// When running in a Google Cloud environment (like Cloud Run), this will automatically
		// use the service account associated with the environment.  No explicit key file is needed.

		try {
			// Attempt to get default credentials.  This will check the environment
			// for appropriate settings.
            GoogleCredentials gCredentials = GoogleCredentials.getApplicationDefault();

			// Check if the credentials object is null
			if (gCredentials == null) {
				throw new IOException("Failed to obtain default credentials.  Ensure your application is running in a Google Cloud environment with a service account, or that you have set the GOOGLE_APPLICATION_CREDENTIALS environment variable.");
			}
			// Ensure that the credentials have been properly scoped.
			 if (gCredentials.createScopedRequired()) {
				gCredentials = gCredentials.createScoped(SCOPES);
			}

			this.credentials = (ServiceAccountCredentials) gCredentials;
			this.credentials.refresh();


		} catch (IOException e) {
			// Handle the case where default credentials cannot be obtained.  This
			// often means the application is not running in a Google Cloud environment
			// or the environment is not configured correctly.
			System.err.println("Error obtaining credentials: " + e.getMessage());
			throw e; // Re-throw the exception so the caller knows that credential acquisition failed.
		}
	}

	@Override
	public String toString() {
		return this.credentials.toString();
	}
}
