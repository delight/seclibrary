
<%@ page import="seclibrary.security.Requestmap" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'requestmap.label', default: 'Requestmap')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-requestmap" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-requestmap" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="url" title="${message(code: 'requestmap.url.label', default: 'Url')}" />
					
						<g:sortableColumn property="configAttribute" title="${message(code: 'requestmap.configAttribute.label', default: 'Config Attribute')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${requestmapInstanceList}" status="i" var="requestmapInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${requestmapInstance.id}">${fieldValue(bean: requestmapInstance, field: "url")}</g:link></td>
					
						<td>${fieldValue(bean: requestmapInstance, field: "configAttribute")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${requestmapInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
