<%@page import="com.umpay.sys.po.Group"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>
<%!
	public String tree(Group group, String basePath) {
		if (group.getChildren().isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ul>" + "\n");
		for(Group o : group.getChildren()) {
			buffer.append("<li><a href=\"" + basePath + "/sys/core/group/list/" + o.getId() + "\" target=\"ajax\" rel=\"jbsxBox2group\">" + o.getName() + "</a>" + "\n");
			buffer.append(tree(o, basePath));
			buffer.append("</li>" + "\n");
		}
		buffer.append("</ul>" + "\n");
		return buffer.toString();
	}
%>
<%
	Group group2 = (Group)request.getAttribute("group");
%>
<div class="pageContent">
	<div class="tabs">

		<div class="tabsContent">
			<div>
				<div layoutH="0" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
			    <ul class="tree treeFolder expand">
					<li><a href="<%=basePath %>/sys/core/group/list/${group.id}" target="ajax" rel="jbsxBox2group">${group.name }</a>
						<%=tree(group2, basePath) %>
					</li>
			     </ul>
				</div>
				
				<div id="jbsxBox2group" class="unitBox" style="margin-left:246px;">
					<!--#include virtual="list1.html" -->
				</div>
	
			</div>
		</div>
	</div>
</div>
