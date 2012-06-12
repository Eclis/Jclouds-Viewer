package eclis.jcloudsviewer.model;

/**
 *
 * @author Eclis
 */
public enum Provider {

    AMAZON("Amazon", "aws-ec2", "aws-s3"), AZURE("Microsoft", "", "ms-blob"),
    GOGRID("GoGrid", "gogrid", "");
    
    private String providerName;
    private String providerCService;
    private String providerBService;

    private Provider(String providerName, String providerCService, String providerBService) {
        this.providerName = providerName;
        this.providerCService = providerCService;
        this.providerBService = providerBService;
        
    }

    public String getProviderName() {
        return providerName;
    }

    @Override
    public String toString() {
        return providerName;
    }

    /**
     * @return the providerCService
     */
    public String getProviderCService() {
        return providerCService;
    }

    /**
     * @param providerCService the providerCService to set
     */
    public void setProviderCService(String providerCService) {
        this.providerCService = providerCService;
    }

    /**
     * @return the providerBService
     */
    public String getProviderBService() {
        return providerBService;
    }

    /**
     * @param providerBService the providerBService to set
     */
    public void setProviderBService(String providerBService) {
        this.providerBService = providerBService;
    }
}
