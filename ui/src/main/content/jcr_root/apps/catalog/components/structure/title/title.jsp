<%@include file="/libs/foundation/global.jsp"%>
<%@ page import="in.shabhushan.catalog.components.structure.header.Title" %>

<c:set var="title" value="<%=new Title(slingRequest) %>"/>

${title.title}
