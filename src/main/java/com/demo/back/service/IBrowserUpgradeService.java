package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browserupgrade.request.BrowserUpgradeRequest;
import com.demo.back.entity.BrowserUpgrade;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface IBrowserUpgradeService {

    PageResponse browserList(BrowserUpgradeRequest request);

    ServiceResponse browserUpdate(BrowserUpgrade request);

    ServiceResponse browserInsert(BrowserUpgrade request);

    ServiceResponse browserUpload(MultipartFile file);

    ServiceResponse browserForClient(String browserType,Integer versionMark);
}
