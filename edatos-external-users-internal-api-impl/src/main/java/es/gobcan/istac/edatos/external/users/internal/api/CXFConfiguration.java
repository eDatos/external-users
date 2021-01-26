package es.gobcan.istac.edatos.external.users.internal.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.xml.bind.Marshaller;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.model.wadl.WadlGenerator;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpInInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPostStreamInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPreStreamInterceptor;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.siemac.edatos.rest.json.EDatosJacksonJaxbJsonProvider;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.StatisticalOperationsRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource({"classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml"})
public class CXFConfiguration {

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public ServletRegistrationBean cxfServletRegistrationBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CXFServlet(), "/apis/*");
        registrationBean.setAsyncSupported(true);
        registrationBean.setLoadOnStartup(1);
        registrationBean.setName("CXFServlet");
        return registrationBean;
    }

    /**
     * Definition of the jaxrs:server.
     * 
     * @see
     *      <ul>
     *      <li><a href=
     *      "http://svn.apache.org/viewvc/cxf/trunk/rt/frontend/jaxrs/src/main/java/org/apache/cxf/jaxrs/spring/SpringResourceServer.java?revision=1548504&view=co&pathrev=1548504">http://svn.apache.org/viewvc/cxf/trunk/rt/frontend/jaxrs/src/main/java/org/apache/cxf/jaxrs/spring/SpringResourceServer.java?revision=1548504&view=co&pathrev=1548504</a></li>
     *      <li><a href=
     *      "http://stackoverflow.com/questions/13520821/autodiscover-jax-rs-resources-with-cxf-in-a-spring-application">http://stackoverflow.com/questions/13520821/autodiscover-jax-rs-resources-with-cxf-in-a-spring-application</a></li>
     *      </ul>
     * @return {@link Server}
     */
    @Bean
    public Server jaxRsServer(final StatisticalOperationsRestInternalFacadeV10 statisticalOperationsRestInternalFacadeV10) {
        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress("/operations-internal");
        factory.setResourceProviders(getResourceProviders());
        factory.setServiceBean(statisticalOperationsRestInternalFacadeV10);
        factory.setBus(ctx.getBean(SpringBus.class));
        factory.setProviders(Arrays.asList(getJaxbProvider(), getJacksonJaxbJsonProvider(), getWadlGenerator()));
        factory.setExtensionMappings(getExtensionMappings());
        factory.setInInterceptors(getInInterceptors());
        factory.setOutInterceptors(getOutInterceptors());
        return factory.create();
    }

    private List<ResourceProvider> getResourceProviders() {
        List<ResourceProvider> resourceProviders = new LinkedList<>();
        for (String beanName : ctx.getBeanDefinitionNames()) {
            if (ctx.findAnnotationOnBean(beanName, Path.class) != null) {
                SpringResourceFactory springResourceFactory = new SpringResourceFactory(beanName);
                springResourceFactory.setApplicationContext(ctx);
                resourceProviders.add(springResourceFactory);
            }
        }
        return resourceProviders;
    }

    private JAXBElementProvider<?> getJaxbProvider() {
        JAXBElementProvider<?> jaxbElementProvider = new JAXBElementProvider<>();
        Map<String, Object> marshallerProperties = new HashMap<>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbElementProvider.setMarshallerProperties(marshallerProperties);
        return jaxbElementProvider;
    }

    private WadlGenerator getWadlGenerator() {
        WadlGenerator wadlGenerator = new WadlGenerator();
        wadlGenerator.setNamespacePrefix("ns");
        return wadlGenerator;
    }

    private JacksonJaxbJsonProvider getJacksonJaxbJsonProvider() {
        return new EDatosJacksonJaxbJsonProvider();
    }

    private Map<Object, Object> getExtensionMappings() {
        Map<Object, Object> mappings = new HashMap<>(2);
        mappings.put("json", "application/json");
        mappings.put("xml", "application/xml");
        return mappings;
    }

    private List<Interceptor<? extends Message>> getInInterceptors() {
        List<Interceptor<? extends Message>> interceptors = new ArrayList<>(1);
        JsonpInInterceptor jsonpInInterceptor = new JsonpInInterceptor();
        jsonpInInterceptor.setCallbackParam("_callback");
        interceptors.add(jsonpInInterceptor);
        return interceptors;
    }

    private List<Interceptor<? extends Message>> getOutInterceptors() {
        List<Interceptor<? extends Message>> interceptors = new ArrayList<>(2);
        interceptors.add(new JsonpPreStreamInterceptor());
        interceptors.add(new JsonpPostStreamInterceptor());
        return interceptors;
    }
}
