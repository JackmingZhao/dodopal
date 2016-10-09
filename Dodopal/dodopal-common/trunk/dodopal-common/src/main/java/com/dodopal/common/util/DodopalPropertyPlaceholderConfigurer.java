package com.dodopal.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

public class DodopalPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(DodopalPropertyPlaceholderConfigurer.class);

    private Resource[] locations;

    private boolean ignoreResourceNotFound = false;

    private String fileEncoding;

    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

    private String propertiesFile;

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public void setLocation(Resource location) {
        this.locations = new Resource[] {location};
    }

    public void setLocations(Resource[] locations) {
        this.locations = locations;
    }

    public void loadProperties(Properties props) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(this.getPropertiesFile());

        System.out.println(sb.toString());

        //动态加载配置文件
        Resource appRes = new FileSystemResource(sb.toString());
        Resource[] srcRes = new Resource[] {appRes};

        if (this.locations != null) {

            Resource[] appRess = Arrays.copyOf(srcRes, this.locations.length + 1);

            System.arraycopy(this.locations, 0, appRess, 1, this.locations.length);
            this.locations = appRess;
        } else {
            this.locations = srcRes;
        }

        for (Resource location : this.locations) {
            if (logger.isInfoEnabled()) {
                logger.info("Loading properties file from " + location);
            }
            InputStream is = null;
            try {
                is = location.getInputStream();

                String filename = null;
                try {
                    filename = location.getFilename();
                }
                catch (IllegalStateException ex) {
                    // resource is not file-based. See SPR-7552.
                }

                if (filename != null && filename.endsWith(".xml")) {
                    this.propertiesPersister.loadFromXml(props, is);
                } else {
                    if (this.fileEncoding != null) {
                        this.propertiesPersister.load(props, new InputStreamReader(is, this.fileEncoding));
                    } else {
                        this.propertiesPersister.load(props, is);
                    }
                }
            }
            catch (IOException ex) {
                if (this.ignoreResourceNotFound) {

                    logger.error("Could not load properties from " + location + ": " + ex.getMessage());

                } else {
                    throw ex;
                }
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
        }
     	DodopalAppVarPropsUtil.copyProperties(props);
    }
}
