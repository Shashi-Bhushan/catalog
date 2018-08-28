Factory
=========
create interface HybrisFactory extends CommerceServiceFactory
create class DefaultHybrisFactory extends AbstractJcrCommerceServiceFactory implements HybrisFactory
DefaultHybrisFactory#getCommerceService gives DefaultHybrisService

Service
========
create class DefaultHybrisService extends AbstractJcrCommerceService implements HybrisService
DefaultHybrisService#login gives CommerceSession 
via return new DefaultHybrisSession(getServiceContext(), service, request, response, sessionInfo, hybrisCookieService);

Session
========
Create class DefaultHybrisSession implements HybrisSession


DefaultHybrisService#getProduct gives product 
via return options.hybrisFactory.getProduct(resolver.getResource(path)); 
