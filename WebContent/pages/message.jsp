<jsp:include page="includes/header.jsp" flush="true"/>

<tr>
 			<td bgcolor="#ffffff" align="center"><br /><br />
            <table width="800" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td align="left" class="barChartHeader"  style="padding-left:50px;">
						<b>Build 2.0.1.5 released on Saturday October 26, 2013 at 12:30PM</b>
						</br></br>
						The following bugs have been fixed:<br/><br/>
						
							<img src="<%=request.getContextPath()%>/images/bug.jpg">donors, addresses, and donations being mismatched when saving ticket</br>
							<img src="<%=request.getContextPath()%>/images/bug.jpg">donors replacing and deleting existing donors when saved</br>
							<img src="<%=request.getContextPath()%>/images/bug.jpg">daily limits not being checked when updating existing ticket</br>
						</ul>
						<br/>
						The following enhancements have been made:
						<ul>
							<li>*ticket zipcodes are verified against the day of week</li>
						</ul>
						
						
						</td>
                    </tr>
            </table>
            </td>
</tr>


<jsp:include page="includes/footer.jsp" flush="true"/>