<!-- bootstrap just to have good looking page -->
<link href="/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="/font-awesome/css/font-awesome.css" rel="stylesheet" >
<link href="/css/user.css" type="text/css" rel="stylesheet">

<%@ page import="de.himbiss.klim.servlets.beans.*, java.util.List, de.himbiss.klim.JSPUtil" %>

<section class="content">
        <div class="card hovercard">
            <div class="cardheader">

            </div>
            <div class="avatar">
                <img alt="" src="<%=((User)request.getAttribute("user")).getAvatarImg()%>">
                    <%
                        User me = (User) request.getAttribute("me");
                        User user = ((User)request.getAttribute("user"));

                        List<User> profileFriends = (List<User>) request.getAttribute("friends");
                        List<User> myFriends = (List<User>) request.getAttribute("my_friends");

                        if (! me.equals(user)) {
                          boolean follow = ! myFriends.contains(user);
                          out.write("<form action=\"/user?action=" + (follow ? "follow" : "unfollow") + "\" method=\"POST\" style=\"display: inline;\">");
                          out.write("<input hidden=\"true\" name=\"profileId\" value=\"" + user.getId() + "\"/>");
                          if (! follow) {
                            out.write("<button class=\"btn_unfollow\">");
                            out.write("<i class=\"fa fa-minus\" aria-hidden=\"true\"></i>");
                            out.write("</button>");
                          }
                          else {
                            out.write("<button class=\"btn_follow\">");
                            out.write("<i class=\"fa fa-plus\" aria-hidden=\"true\"></i>");
                            out.write("</button>");
                          }
                          out.write("</form>");
                        }
                    %>
                </button>
            </div>
            <div class="info">
                <div class="title">
                    <a><%=((User)request.getAttribute("user")).getUserName()%></a>
                </div>
                <%
                    for (String desc : user.getDescription()) {
                        out.write("<div class=\"desc\">" + desc + "</div>");
                    }
                %>
            </div>
            <div class="bottom">
                <a class="btn btn-primary btn-twitter btn-sm" href="https://twitter.com/webmaniac">
                    <i class="fa fa-twitter"></i>
                </a>
                <a class="btn btn-danger btn-sm" rel="publisher"
                   href="https://plus.google.com/+ahmshahnuralam">
                    <i class="fa fa-google-plus"></i>
                </a>
                <a class="btn btn-primary btn-sm" rel="publisher"
                   href="https://plus.google.com/shahnuralam">
                    <i class="fa fa-facebook"></i>
                </a>
                <a class="btn btn-warning btn-sm" rel="publisher" href="https://plus.google.com/shahnuralam">
                    <i class="fa fa-behance"></i>
                </a>
            </div>
        </div>

    <div class="contentboard">
        <div class="feed">
            <div class="submit">
                <%
                    JSPUtil.createChip(request.getContextPath(), out, me);
                %>
                <form action="/user?action=submit_post" method="POST">
                  <textarea name="content" placeholder="<%= user.getUserName().equals(me.getUserName()) ? "How do you feel today? :)" : "Write something nice :)"%>"></textarea>
                  <input hidden="true" name="profileId" value="<%= user.getId() %>"/>
                  <button>post</button>
                </form>
            </div>

            <%
                List<Post> posts = (List<Post>) request.getAttribute("posts");
                for (Post post : posts) {
                    out.write("<div class=\"post\">");
                    JSPUtil.createChip(request.getContextPath(), out, post.getFrom());
                    out.write("<p align=\"left\" class=\"content_chip\">" + post.getContent() + "</p>");
                    out.write("</div>");
                }
            %>

        </div>
    </div>
</section>