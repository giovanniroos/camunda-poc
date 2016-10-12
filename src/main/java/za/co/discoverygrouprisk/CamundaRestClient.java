//package za.co.discoverygrouprisk;
//
///*
//* Copyright (c) Discovery Holdings Ltd. All Rights Reserved.
//*
//* This software is the confidential and proprietary information of
//* Discovery Holdings Ltd ("Confidential Information").
//* It may not be copied or reproduced in any manner without the express
//* written permission of Discovery Holdings Ltd.
//*
//*/
//
//import org.json.JSONStringer;
//import org.restlet.Client;
//import org.restlet.Context;
//import org.restlet.data.ChallengeScheme;
//import org.restlet.data.MediaType;
//import org.restlet.data.Protocol;
//import org.restlet.ext.json.JsonRepresentation;
//import org.restlet.representation.Representation;
//import org.restlet.resource.ClientResource;
//import org.restlet.resource.ResourceException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class CamundaRestClient {
//  private static final Logger logger = LoggerFactory.getLogger(CamundaRestClient.class.getName());
//  public String CAMUNDA_URL = "http://localhost:8280/process-controller";
//  private Client client;
//  private String loggedInUser;
//  private String host;
//  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//
//  public CamundaRestClient(String loggedInUser, String host) {
//    createCamundaUrl();
//    this.loggedInUser = loggedInUser;
//    this.host = host;
//    client = new Client(new Context(), Protocol.HTTP);
//  }
//
//  public CamundaRestClient(String loggedInUser) {
//    createCamundaUrl();
//    this.loggedInUser = loggedInUser;
//    client = new Client(new Context(), Protocol.HTTP);
//  }
//
//  public CamundaRestClient(String loggedInUser, int rowIndex, int pageSize) {
//    createCamundaUrl();
//    this.loggedInUser = loggedInUser;
//    client = new Client(new Context(), Protocol.HTTP);
//  }
//
//  public CamundaRestClient(String loggedInUser, String host, String urlMethodPrefix) {
//    CAMUNDA_URL = CAMUNDA_URL + urlMethodPrefix;
//    this.loggedInUser = loggedInUser;
//    this.host = host;
//    client = new Client(new Context(), Protocol.HTTP);
//  }
//
//  private void createCamundaUrl() {
//    try {
//      String urlMethodPrefix = "/engine/default"; //MessageBundleUtil.getMessage("general", "camunda_rest_url_prefix");
//      CAMUNDA_URL = CAMUNDA_URL + urlMethodPrefix;
//    } catch (Exception e) {
//      e.printStackTrace();
//      logger.info("Error in CamundaRestClient#createCamundaUrl. Cannot lookup the camunda_rest_url_prefix. Use " +
//          "'default' value");
//    }
//  }
//
//  private ClientResource createClientResource(String URL) {
//    String resourceUrl = getCamundaURL() + URL;
//    logger.debug("createClientResource=" + resourceUrl);
//    ClientResource clientResource = new ClientResource(resourceUrl);
//    clientResource.setNext(client);
//    clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, loggedInUser, loggedInUser);
//    return clientResource;
//  }
//
//  private Representation doCamundaPost(String URL, JSONStringer object) {
//    Representation request = null;
//    Representation response = null;
//    ClientResource clientResource = createClientResource(URL);
//    try {
//      request = new JsonRepresentation(object);
//      request.setMediaType(MediaType.APPLICATION_JSON);
//      response = clientResource.post(request);
//    } catch (ResourceException ex) {
//      logger.error("Error while sending the POST REST request to camunda: " + URL);
//    } finally {
//      clientResource.release();
//    }
//    return response;
//  }
//
//  public boolean sendMessage(String messageName, String businessKey, Map<String, String> processVariables, Map<String,
//      String> correlationKeys) {
//    try {
//      String url = "/message";
//      JSONStringer json = new JSONStringer();
//      json.object();
//      json.key("messageName").value(messageName);
//      json.key("businessKey").value(businessKey);
//      //process vars
//      json.key("processVariables").object();
//      if (processVariables != null && processVariables.size() > 0) {
//        for (Map.Entry entry : processVariables.entrySet()) {
//          json.key(entry.getKey().toString()).object();
//          json.key("value").value(entry.getValue().toString());
//          json.endObject();
//        }
//      }
//      json.endObject();
//      //correlation keys
//      if (correlationKeys != null && correlationKeys.size() > 0) {
//        json.key("correlationKeys").object();
//        for (Map.Entry entry : correlationKeys.entrySet()) {
//          json.key(entry.getKey().toString()).object();
//          json.key("value").value(entry.getValue().toString());
//          json.endObject();
//        }
//        json.endObject();
//      }
//      json.endObject();
//      Representation representation = doCamundaPost(url, json);
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    }
//    return true;
//  }
//
//  public String getHost() {
//    return host;
//  }
//
//  public void setHost(String host) {
//    this.host = host;
//  }
//
//  private String getCamundaURL() {
//    if (getHost() != null) {
//      CAMUNDA_URL = CAMUNDA_URL.replace("localhost", getHost());
//    }
//    return CAMUNDA_URL;
//  }
//}
//
//
