package ru.shakurov.demo;

public class WebAppInitializer /*extends AbstractAnnotationConfigDispatcherServletInitializer*/ {

/*
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ApplicationConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        FilterRegistration.Dynamic securityFilter = servletContext.addFilter(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, DelegatingFilterProxy.class);
        securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
        return securityFilter;
    }
*/

}
