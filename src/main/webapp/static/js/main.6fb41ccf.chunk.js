(this.webpackJsonpvie=this.webpackJsonpvie||[]).push([[0],{130:function(e,t,n){},131:function(e,t,n){},171:function(e,t,n){"use strict";n.r(t);var a=n(0),r=n.n(a),o=n(12),c=n.n(o),s=(n(130),n(131),n(49)),i=n(7),l=n(35),j=n(4),b=n(209),d=n(224),u=n(223),O=n(210),p=n(211),x=n(212),h=n(208),g=n(207),f=n(64),m=n.n(f),y=n(213),v=n(214),w=n(219),S=n(221),I=n(41),C=n.n(I),N=n(1);function k(){var e=r.a.useState(""),t=Object(i.a)(e,2),n=t[0],o=t[1],c=Object(a.useState)([]),s=Object(i.a)(c,2),j=s[0],b=s[1],d=function(){};function u(){"yes"===localStorage.getItem("hasRequested")&&(j=[]),C.a.get("https://amazing-office-313314.appspot.com/rest/find/all").then((function(e){var t=e.data;console.log(t);for(var n=0;n<t.length;n++){var a=t[n];j.push(a)}b(j)})),localStorage.setItem("hasRequested","yes")}function O(e){"Enter"===e.key&&(document.location.href="/profile/"+n)}return Object(N.jsx)("div",{style:{width:500,backgroundColor:"white"},children:Object(N.jsx)(S.a,{style:{width:500},freeSolo:!0,autoComplete:!0,autoHighlight:!0,options:j,onChange:function(e,t){return o(t)},renderInput:function(e){return Object(N.jsx)(w.a,Object(l.a)(Object(l.a)({},e),{},{onChange:d,variant:"filled",label:"Search Box",onKeyPress:O,onClick:u}))}})})}var P=n.p+"static/media/logo.02879430.svg",A=240,R=Object(b.a)((function(e){return Object(d.a)({root:{display:"flex"},appBar:{transition:e.transitions.create(["margin","width"],{easing:e.transitions.easing.sharp,duration:e.transitions.duration.leavingScreen})},appBarShift:{width:"calc(100% - ".concat(A,"px)"),marginLeft:A,transition:e.transitions.create(["margin","width"],{easing:e.transitions.easing.easeOut,duration:e.transitions.duration.enteringScreen})},menuButton:{marginRight:e.spacing(2)},hide:{display:"none"},drawer:{width:A,flexShrink:0},drawerPaper:{width:A},drawerHeader:Object(l.a)(Object(l.a)({display:"flex",alignItems:"center",padding:e.spacing(0,1)},e.mixins.toolbar),{},{justifyContent:"flex-end"}),content:{flexGrow:1,padding:e.spacing(3),transition:e.transitions.create("margin",{easing:e.transitions.easing.sharp,duration:e.transitions.duration.leavingScreen}),marginLeft:-240},contentShift:{transition:e.transitions.create("margin",{easing:e.transitions.easing.easeOut,duration:e.transitions.duration.enteringScreen}),marginLeft:0}})}));function T(){var e=R(),t=r.a.useState(!1),n=Object(i.a)(t,2),a=n[0],o=n[1];return Object(N.jsxs)("div",{className:e.root,children:[Object(N.jsx)(O.a,{}),Object(N.jsx)(p.a,{position:"fixed",className:Object(j.a)(e.appBar,Object(s.a)({},e.appBarShift,a)),children:Object(N.jsxs)(x.a,{style:{background:"#1B3651",display:"flex",justifyContent:"space-between"},children:[Object(N.jsx)(g.a,{color:"inherit","aria-label":"open drawer",onClick:function(){o(!a)},edge:"start",className:Object(j.a)(e.menuButton,a),children:Object(N.jsx)(m.a,{})}),Object(N.jsx)("a",{href:"/home",children:Object(N.jsx)("img",{src:P,alt:"logo",width:"55px",style:{position:"fixed",top:"5px",left:"738px"}})}),Object(N.jsx)(k,{})]})}),Object(N.jsx)(u.a,{className:e.drawer,variant:"persistent",anchor:"left",open:a,classes:{paper:e.drawerPaper},children:Object(N.jsx)(h.a,{children:["Home","Login","Register","About"].map((function(e){return Object(N.jsx)(y.a,{button:!0,component:"a",href:"/"+e,children:Object(N.jsx)(v.a,{primary:e})},e)}))})})]})}var D=n(107),U=n(13),z=n(220),B=n(215),F=n(65),L=n.n(F);n(45);var q=function(){var e=Object(a.useState)(""),t=Object(i.a)(e,2),n=t[0],r=t[1],o=Object(a.useState)(""),c=Object(i.a)(o,2),s=c[0],l=c[1],j=Object(a.useState)(""),u=Object(i.a)(j,2),O=u[0],p=u[1],x=Object(a.useState)(""),h=Object(i.a)(x,2),g=h[0],f=h[1],m=Object(a.useState)(""),y=Object(i.a)(m,2),v=y[0],w=y[1],S=Object(a.useState)(""),I=Object(i.a)(S,2),C=I[0],k=I[1],P=Object(a.useState)(""),A=Object(i.a)(P,2),R=A[0],T=A[1],D=Object(a.useState)(""),U=Object(i.a)(D,2),F=U[0],q=U[1],M=Object(a.useState)(""),E=Object(i.a)(M,2),J=E[0],G=E[1],H=[];function V(){return JSON.stringify({firstName:n,lastName:s,username:O,email:g,password:v,year:J,month:F,day:R})}var W=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{className:"container",style:{transform:"scale(1.35)"},children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:3/4,textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsxs)("form",{onSubmit:function(e){e.preventDefault()},children:[Object(N.jsx)("label",{style:{color:"white"},children:"First Name:"}),Object(N.jsx)("input",{type:"text",value:n,onChange:function(e){r(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Last Name:"}),Object(N.jsx)("input",{type:"text",value:s,onChange:function(e){l(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Username:"}),Object(N.jsx)("input",{type:"text",value:O,onChange:function(e){p(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Email:"}),Object(N.jsx)("input",{type:"text",value:g,onChange:function(e){f(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Password:"}),Object(N.jsx)("input",{type:"password",value:v,onChange:function(e){w(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Confirm Password:"}),Object(N.jsx)("input",{type:"password",value:C,onChange:function(e){k(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Birthday:"}),Object(N.jsx)("input",{type:"date",onChange:function(e){H=e.target.value.split("-"),G(H[0]),q(H[1]),T(H[2])},required:!0})]}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",className:W.button,startIcon:Object(N.jsx)(L.a,{}),onClick:function(){console.log("Registering User"),console.log(V()),""!==n.toString()&&""!==s.toString()&&""!==O.toString()&&""!==g.toString()&&""!==v.toString()&&""!==C.toString()&&""!==R.toString()?g.toString().includes("@")?v.toString().length>9?C.toString()===v.toString()?O.includes(" ")?alert("Username cannot contain empty spaces"):fetch("https://amazing-office-313314.appspot.com/rest/register",{method:"POST",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:V()}).then((function(e){e.ok?(console.log(e),console.log("Logging In"),console.log(V()),fetch("https://amazing-office-313314.appspot.com/rest/login",{method:"POST",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:JSON.stringify({username:O,password:v})}).then((function(e){e.json().then((function(e){localStorage.setItem("tokenID",e.tokenID),localStorage.setItem("username",e.username),localStorage.setItem("role",e.role),window.location.href="/feed"}))}))):alert("Username already in use")})):alert("Pasword do not match"):alert("Pasword must contain at least 10 characters"):alert("Not a valid Email"):alert("All Fields Are Mandatory")},children:"Register"})]})})]})};var M=function(){var e=Object(a.useState)(""),t=Object(i.a)(e,2),n=t[0],r=t[1],o=Object(a.useState)(""),c=Object(i.a)(o,2),s=c[0],l=c[1],j=Object(a.useState)(""),u=Object(i.a)(j,2),O=u[0],p=u[1],x=Object(a.useState)(""),h=Object(i.a)(x,2),g=h[0],f=h[1],m=Object(a.useState)(""),y=Object(i.a)(m,2),v=y[0],w=y[1],S=Object(a.useState)(""),I=Object(i.a)(S,2),C=I[0],k=I[1],P=Object(a.useState)(""),A=Object(i.a)(P,2),R=A[0],T=A[1],D=Object(a.useState)(""),U=Object(i.a)(D,2),F=U[0],q=U[1];function M(){return JSON.stringify({name:n,owner:s,email:O,password:g,id:C,address:R,serviceType:F})}var E=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{className:"container",style:{transform:"scale(1.35)"},children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:3/4,textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsxs)("form",{onSubmit:function(e){e.preventDefault()},children:[Object(N.jsx)("label",{style:{color:"white"},children:"Organization Name:"}),Object(N.jsx)("input",{type:"text",value:n,onChange:function(e){r(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Owner's Name:"}),Object(N.jsx)("input",{type:"text",value:s,onChange:function(e){l(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Email:"}),Object(N.jsx)("input",{type:"text",value:O,onChange:function(e){p(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Password:"}),Object(N.jsx)("input",{type:"password",value:g,onChange:function(e){f(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Confirm Password:"}),Object(N.jsx)("input",{type:"password",value:v,onChange:function(e){w(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"CardID:"}),Object(N.jsx)("input",{type:"text",value:C,onChange:function(e){k(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Address:"}),Object(N.jsx)("input",{type:"text",value:R,onChange:function(e){T(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Service Type:"}),Object(N.jsx)("input",{type:"text",value:F,onChange:function(e){q(e.target.value)},required:!0})]}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",className:E.button,startIcon:Object(N.jsx)(L.a,{}),onClick:function(){console.log("Registering Organisation"),console.log(M()),""!==n.toString()&&""!==s.toString()&&""!==O.toString()&&""!==g.toString()&&""!==v.toString()&&""!==C.toString()&&""!==R.toString()&&""!==F.toString()?O.toString().includes("@")?g.toString().length>9?v.toString()===g.toString()?fetch("https://amazing-office-313314.appspot.com/rest/register/organisation",{method:"POST",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:M()}).then((function(e){e.ok?(console.log(e),console.log("Logging In"),console.log(M()),fetch("https://amazing-office-313314.appspot.com/rest/login",{method:"POST",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:JSON.stringify({username:O,password:g})}).then((function(e){e.json().then((function(e){localStorage.setItem("tokenID",e.tokenID),localStorage.setItem("username",e.username),localStorage.setItem("role",e.role),window.location.href="/feed"}))}))):alert("Email already in use")})):alert("Pasword do not match"):alert("Pasword must contain at least 10 characters"):alert("Not a valid Email"):alert("All Fields Are Mandatory")},children:"Register"})]})})]})},E=n(56),J=n(98),G=n.n(J),H=n(99),V=n.n(H);var W=function(){var e=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{className:"container",style:{transform:"scale(1.5)"},children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsx)(E.a,{style:{color:"white",fontSize:"20px",width:"500px"},children:"Which type of account would you like to Register?"}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",href:"/register_user",className:e.button,startIcon:Object(N.jsx)(G.a,{}),children:"User"}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",href:"/register_organization",className:e.button,startIcon:Object(N.jsx)(V.a,{}),children:"Organization"})]})})]})},_=n(66),K=n.n(_);var Y=function(){var e=localStorage.getItem("username"),t=localStorage.getItem("tokenID"),n=Object(a.useState)(""),r=Object(i.a)(n,2),o=r[0],c=r[1];function s(){return JSON.stringify({tokenID:t,username:e,password:o})}var l=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:.5,textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,style:{transform:"scale(1.5)"},children:[Object(N.jsxs)(E.a,{style:{color:"white",fontSize:"20px"},children:["This will delete your account.",Object(N.jsx)("br",{}),"If really want to continue confirm your password and press the button below."]}),Object(N.jsx)("br",{}),Object(N.jsx)("input",{type:"password",value:o,onChange:function(e){c(e.target.value)},required:!0}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",className:l.button,onClick:function(){console.log("Deleting User"),console.log(s()),fetch("https://amazing-office-313314.appspot.com/rest/delete",{method:"DELETE",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:s()}).then((function(e){e.ok?(console.log(e),console.log(e.body),window.location.href="/Home",localStorage.removeItem("username"),localStorage.removeItem("tokenID")):alert("Incorrect Password")}))},startIcon:Object(N.jsx)(K.a,{}),children:"Delete"})]})})]})},Q=n(216),X=n(67),Z=n.n(X);var $=function(){var e=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:.5,textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsx)("br",{}),Object(N.jsxs)(E.a,{variant:"h2",style:{color:"white"},children:[Object(N.jsx)("strong",{children:"Vie"})," by Abris"]}),Object(N.jsx)("br",{}),Object(N.jsxs)(E.a,{style:{color:"white",fontSize:"20px"},children:["Ever wanted to be a volunteer but didn't know where to start?",Object(N.jsx)("br",{}),"Sign up and begin your jorney to change the world!"]}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{href:"/register",variant:"contained",color:"primary",className:e.button,startIcon:Object(N.jsx)(Z.a,{}),children:"Create an account"}),Object(N.jsx)("br",{}),Object(N.jsx)(Q.a,{href:"/login",style:{color:"white"},children:" I already have an account."}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{})]})})]})};var ee=function(){var e=Object(a.useState)(""),t=Object(i.a)(e,2),n=t[0],r=t[1],o=Object(a.useState)(""),c=Object(i.a)(o,2),s=c[0],l=c[1];function j(){return JSON.stringify({username:n,password:s})}var u=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{className:"container",style:{transform:"scale(1.5)"},children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:3/4,textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsxs)("form",{onSubmit:function(e){e.preventDefault()},children:[Object(N.jsx)("label",{style:{color:"white"},children:"Username:"}),Object(N.jsx)("input",{type:"text",value:n,onChange:function(e){r(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Password:"}),Object(N.jsx)("input",{type:"password",value:s,onChange:function(e){l(e.target.value)},required:!0})]}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{onClick:function(){console.log("Logging In"),console.log(j()),""!==n.toString()&&""!==s.toString()?fetch("https://amazing-office-313314.appspot.com/rest/login",{method:"POST",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:j()}).then((function(e){e.ok?e.json().then((function(e){localStorage.setItem("tokenID",e.tokenID),localStorage.setItem("username",e.username),localStorage.setItem("role",e.role),localStorage.setItem("hasReloaded","nop"),window.location.href="/feed"})):alert("Incorrect Username or Password")})):alert("All Fields Are Mandatory")},variant:"contained",color:"primary",className:u.button,startIcon:Object(N.jsx)(Z.a,{}),children:"Login"})]})})]})},te=n(100),ne=n.n(te);var ae=function(){return Object(N.jsxs)("div",{children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:.5,textAlign:"justify",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsx)("span",{style:{color:"white",fontSize:"50px",display:"flex",justifyContent:"flex-start"},children:Object(N.jsx)("strong",{children:"Welcome to Vie!"})}),Object(N.jsx)("br",{}),Object(N.jsxs)(E.a,{style:{color:"white",fontSize:"20px"},children:["Vie is an app that has as a goal to organize and to make it acessible to make volunteering.",Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),"Our team, Abris, is composed by 5 portuguese computer science students from Faculdade de Ci\xeancias e Tecnologia da Universidade Nova de Lisboa, who where given the challenge by the teachers to make an app that could change the world.",Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),"Have any recomendation? Feel free to send us an email!",Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)(ne.a,{})," \xa0 abris.apdc@gmail.com"]})]})})]})},re=n(101),oe=n.n(re);var ce=function(){var e=Object(a.useState)(""),t=Object(i.a)(e,2),n=t[0],r=t[1],o=Object(a.useState)(""),c=Object(i.a)(o,2),s=c[0],l=c[1],j=Object(a.useState)(""),u=Object(i.a)(j,2),O=u[0],p=u[1],x=Object(a.useState)(""),h=Object(i.a)(x,2),g=h[0],f=h[1],m=Object(a.useState)(""),y=Object(i.a)(m,2),v=y[0],w=y[1],S=Object(a.useState)(""),I=Object(i.a)(S,2),C=I[0],k=I[1],P=Object(a.useState)(""),A=Object(i.a)(P,2),R=A[0],T=A[1],D=Object(a.useState)(""),U=Object(i.a)(D,2),F=U[0],L=U[1],q=Object(a.useState)(""),M=Object(i.a)(q,2),E=M[0],J=M[1],G=Object(a.useState)(""),H=Object(i.a)(G,2),V=H[0],W=H[1],_=localStorage.getItem("tokenID");function K(){return JSON.stringify({firstName:n,lastName:s,email:O,gender:g,phoneNumber:v,address:C,nationality:R,firstLanguage:F,description:E,perfil:V,tokenID:_})}var Y=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{className:"container",style:{transform:"scale(1.20)"},children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:1,textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsxs)("form",{onSubmit:function(e){e.preventDefault()},children:[Object(N.jsx)("label",{style:{color:"white"},children:"First Name:"}),Object(N.jsx)("input",{type:"text",value:n,onChange:function(e){r(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Last Name:"}),Object(N.jsx)("input",{type:"text",value:s,onChange:function(e){l(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Email:"}),Object(N.jsx)("input",{type:"text",value:O,onChange:function(e){p(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Gender:"}),"\xb4",Object(N.jsx)("br",{}),Object(N.jsxs)("select",{name:g,onChange:function(e){f(e.target.value)},children:[Object(N.jsx)("option",{value:""}),Object(N.jsx)("option",{value:"Male",children:"Male"}),Object(N.jsx)("option",{value:"Female",children:"Female"}),Object(N.jsx)("option",{value:"Non-Binary",children:"Non-Binary"}),Object(N.jsx)("option",{value:"Other",children:"Other"})]}),Object(N.jsx)("br",{}),Object(N.jsx)("label",{style:{color:"white"},children:"Phone Number:"}),Object(N.jsx)("input",{type:"text",value:v,onChange:function(e){w(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Address:"}),Object(N.jsx)("input",{type:"text",value:C,onChange:function(e){k(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Nationality:"}),Object(N.jsx)("input",{type:"text",value:R,onChange:function(e){T(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Language:"}),Object(N.jsx)("input",{type:"text",value:F,onChange:function(e){L(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Bio:"}),Object(N.jsx)("input",{type:"text",value:E,height:50,onChange:function(e){J(e.target.value)}}),Object(N.jsx)("label",{style:{color:"white"},children:"Profile Visibility:"}),Object(N.jsx)("br",{}),Object(N.jsxs)("select",{name:V,onChange:function(e){W(e.target.value)},children:[Object(N.jsx)("option",{value:""}),Object(N.jsx)("option",{value:"Publico",children:"Public"}),Object(N.jsx)("option",{value:"Privado",children:"Private"})]})]}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",className:Y.button,startIcon:Object(N.jsx)(oe.a,{}),onClick:function(){console.log("Updating User"),console.log(K()),console.log(g),O.toString().includes("@")||""===O.toString()?9===v.toString().length||""===v.toString()?fetch("https://amazing-office-313314.appspot.com/rest/update",{method:"PUT",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:K()}).then((function(e){e.ok?(alert("Update Successful"),console.log(e),window.location.href="/myProfile"):alert("Something went wrong")})):alert("Invalid Phone Number"):alert("Not a valid Email")},children:"Update"})]})})]})},se=n(217),ie=240,le=Object(b.a)((function(e){return Object(d.a)({root:{display:"flex"},appBar:{transition:e.transitions.create(["margin","width"],{easing:e.transitions.easing.sharp,duration:e.transitions.duration.leavingScreen})},appBarShift:{width:"calc(100% - ".concat(ie,"px)"),marginLeft:ie,transition:e.transitions.create(["margin","width"],{easing:e.transitions.easing.easeOut,duration:e.transitions.duration.enteringScreen})},menuButton:{marginRight:e.spacing(2)},hide:{display:"none"},drawer:{width:ie,flexShrink:0},drawerPaper:{width:ie},drawerHeader:Object(l.a)(Object(l.a)({display:"flex",alignItems:"center",padding:e.spacing(0,1)},e.mixins.toolbar),{},{justifyContent:"flex-end"}),content:{flexGrow:1,padding:e.spacing(3),transition:e.transitions.create("margin",{easing:e.transitions.easing.sharp,duration:e.transitions.duration.leavingScreen}),marginLeft:-240},contentShift:{transition:e.transitions.create("margin",{easing:e.transitions.easing.easeOut,duration:e.transitions.duration.enteringScreen}),marginLeft:0}})}));function je(){var e=le(),t=r.a.useState(!1),n=Object(i.a)(t,2),a=n[0],o=n[1];return Object(N.jsxs)("div",{className:e.root,children:[Object(N.jsx)(O.a,{}),Object(N.jsx)(p.a,{position:"fixed",className:Object(j.a)(e.appBar,Object(s.a)({},e.appBarShift,a)),children:Object(N.jsxs)(x.a,{style:{background:"#1B3651",display:"flex",justifyContent:"space-between"},children:[Object(N.jsx)(g.a,{color:"inherit","aria-label":"open drawer",onClick:function(){o(!a)},edge:"start",className:Object(j.a)(e.menuButton,a),children:Object(N.jsx)(m.a,{})}),Object(N.jsx)("a",{href:"/home",children:Object(N.jsx)("img",{src:P,alt:"logo",width:"55px",style:{position:"fixed",top:"5px",left:"738px"}})}),Object(N.jsx)(k,{})]})}),Object(N.jsxs)(u.a,{className:e.drawer,variant:"persistent",anchor:"left",open:a,classes:{paper:e.drawerPaper},children:[Object(N.jsx)(h.a,{children:["Feed","myProfile","About","Map"].map((function(e){return Object(N.jsx)(y.a,{button:!0,component:"a",href:"/"+e,children:Object(N.jsx)(v.a,{primary:e})},e)}))}),Object(N.jsx)(se.a,{}),Object(N.jsx)(h.a,{children:["Logout"].map((function(e){return Object(N.jsx)(y.a,{button:!0,component:"a",href:"/"+e,children:Object(N.jsx)(v.a,{primary:e})},e)}))})]})]})}var be,de,ue,Oe,pe,xe,he,ge,fe=n(218),me=n(103),ye=n.n(me),ve=n(68),we=n.n(ve),Se=n(102),Ie=n.n(Se);var Ce=function(){var e=Object(a.useState)(!0),t=Object(i.a)(e,2),n=t[0],r=t[1];C.a.get("https://amazing-office-313314.appspot.com/rest/profile/"+localStorage.getItem("username")).then((function(e){var t=e.data;be=t.firstName,de=t.lastName,ue=t.role,r(!1)})),C.a.get("https://amazing-office-313314.appspot.com/rest/profile/get/"+localStorage.getItem("username")).then((function(e){var t=e.data;console.log(t),Oe=t.birthday,pe=t.email,xe=t.followers,he=t.following,ge=t.phone}));var o=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return n?Object(N.jsxs)("div",{style:{color:"white"},className:"App",children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),"Loading...",Object(N.jsx)("br",{}),Object(N.jsx)(fe.a,{style:{color:"white"}})]}):Object(N.jsxs)("div",{style:{transform:"2000px",alignItems:"center"},children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("span",{style:{color:"white",fontSize:"30px"},children:Object(N.jsx)("b",{children:localStorage.getItem("username")})}),Object(N.jsx)("span",{style:{color:"white",fontSize:"29px"},children:"'s profile."}),Object(N.jsx)("br",{}),Object(N.jsxs)(B.a,{variant:"contained",color:"primary",href:"/profile/"+localStorage.getItem("username")+"/followers",className:o.button,children:[Object(N.jsx)("b",{children:"Followers: "})," ",xe]}),Object(N.jsxs)(B.a,{variant:"contained",color:"primary",href:"/profile/"+localStorage.getItem("username")+"/following",className:o.button,children:[Object(N.jsx)("b",{children:"Following: "})," ",he]}),Object(N.jsx)("br",{}),Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:.5,textAlign:"Left",p:2,borderRadius:"borderRadius",boxShadow:2,alignSelf:"center",children:[Object(N.jsxs)("span",{style:{color:"white",fontSize:"20px"},children:["About ",localStorage.getItem("username"),":"]}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white",fontSize:"15px"},children:[Object(N.jsx)("b",{children:"Name:"})," ",be,"\xa0",de]}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white",fontSize:"15px"},children:[Object(N.jsx)("b",{children:"Birthday:"})," ",Oe]}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white",fontSize:"15px"},children:[Object(N.jsx)("b",{children:"Email:"})," ",pe]}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white",fontSize:"15px"},children:[Object(N.jsx)("b",{children:"Phone:"})," ",ge]}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white",fontSize:"15px"},children:[Object(N.jsx)("b",{children:"Role:"})," ",ue]})]}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",href:"/profile/"+localStorage.getItem("username"),className:o.button,startIcon:Object(N.jsx)(Ie.a,{}),children:"Check how your profile looks to public eye!"}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",href:"/update",className:o.button,style:{width:"210px"},startIcon:Object(N.jsx)(ye.a,{}),children:"update Info"}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",href:"/password",style:{width:"210px"},className:o.button,startIcon:Object(N.jsx)(we.a,{}),children:"change password"}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",href:"/delete",style:{width:"210px"},className:o.button,startIcon:Object(N.jsx)(K.a,{}),children:"Delete account"})]})};var Ne,ke,Pe,Ae=function(){var e=Object(a.useState)(""),t=Object(i.a)(e,2),n=t[0],r=t[1],o=Object(a.useState)(""),c=Object(i.a)(o,2),s=c[0],l=c[1],j=Object(a.useState)(""),u=Object(i.a)(j,2),O=u[0],p=u[1];function x(){return JSON.stringify({oldPass:n,newPass:s,confirmation:O,tokenID:localStorage.getItem("tokenID")})}var h=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();return Object(N.jsxs)("div",{className:"container",style:{transform:"scale(1.50)"},children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("div",{style:{display:"flex",justifyContent:"center"},children:Object(N.jsxs)(z.a,{bgcolor:"#1B3651",width:"100%",textAlign:"center",p:2,borderRadius:"borderRadius",boxShadow:2,children:[Object(N.jsxs)("form",{onSubmit:function(e){e.preventDefault()},children:[Object(N.jsx)("label",{style:{color:"white"},children:"Current Password:"}),Object(N.jsx)("input",{type:"password",value:n,onChange:function(e){r(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"New Password:"}),Object(N.jsx)("input",{type:"password",value:s,onChange:function(e){l(e.target.value)},required:!0}),Object(N.jsx)("label",{style:{color:"white"},children:"Confirm Password:"}),Object(N.jsx)("input",{type:"password",value:O,onChange:function(e){p(e.target.value)},required:!0})]}),Object(N.jsx)("br",{}),Object(N.jsx)(B.a,{variant:"contained",color:"primary",onClick:function(){console.log("Registering User"),console.log(x()),""!==n.toString()&&""!==s.toString()&&""!==O.toString()?s.toString().length>9?O.toString()===s.toString()?fetch("https://amazing-office-313314.appspot.com/rest/update/pass",{method:"PUT",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:x()}).then((function(e){e.ok?(alert("Update Successful"),console.log(e),window.location.href="/loggedIn/Profile"):alert("Wrong password")})):alert("Pasword do not match"):alert("New Pasword must Contain at Least 10 characters"):alert("All Fields Are Mandatory")},className:h.button,startIcon:Object(N.jsx)(we.a,{}),children:"Change Password"})]})})]})},Re=n(104),Te=n.n(Re);var De=function(e){var t=JSON.stringify(e).split(':"')[1].split('"}')[0],n=Object(a.useState)(!0),r=Object(i.a)(n,2),o=r[0],c=r[1],s=Object(a.useState)(!1),l=Object(i.a)(s,2),j=l[0],u=l[1],O=Object(a.useState)(!1),p=Object(i.a)(O,2),x=p[0],h=p[1];C.a.get("https://amazing-office-313314.appspot.com/rest/getFollowing/"+localStorage.getItem("username")).then((function(e){for(var n=e.data,a=0;a<n.length;a++)n[a]===t&&h(!0)})),C.a.get("https://amazing-office-313314.appspot.com/rest/profile/"+t).then((function(e){var t=e.data;Ne=t.firstName,ke=t.lastName,Pe=t.role,localStorage.setItem("currentProfile",Pe),c(!1)})).catch((function(e){console.log(e),c(!1),u(!0)}));var g=Object(b.a)((function(e){return Object(d.a)({button:{margin:e.spacing(1)}})}))();function f(e){console.log(Pe),console.log(e.target.value),""!==e.target.value&&(Pe!==e.target.value?(Pe=e.target.value,fetch("https://amazing-office-313314.appspot.com/rest/changeRole/",{method:"PUT",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:JSON.stringify({username:t,tokenID:localStorage.getItem("tokenID"),newRole:Pe})}).then((function(e){e.ok||alert("Something went wrong."),window.location.reload()}))):alert("You can't change to actual role."))}function m(){return JSON.stringify({tokenID:localStorage.getItem("tokenID")})}if(o)return Object(N.jsxs)("div",{style:{color:"white"},className:"App",children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),"Loading...",Object(N.jsx)("br",{}),Object(N.jsx)(fe.a,{style:{color:"white"}})]});if(j)return Object(N.jsxs)("div",{style:{color:"white"},className:"App",children:[Object(N.jsx)("br",{}),"No account with given username."]});var y=Object(N.jsx)("p",{});localStorage.getItem("username")===t&&(y=Object(N.jsx)(B.a,{variant:"contained",color:"primary",href:"/myProfile",className:g.button,startIcon:Object(N.jsx)(Te.a,{}),children:"go back"}));var v=Object(N.jsx)("p",{});"SU"===localStorage.getItem("role")&&"SU"!==Pe&&"ORG"!==Pe&&(v=Object(N.jsxs)("select",{name:Pe,onChange:f,children:[Object(N.jsx)("option",{value:""}),Object(N.jsx)("option",{value:"USER",children:"USER"}),Object(N.jsx)("option",{value:"MOD",children:"MOD"}),Object(N.jsx)("option",{value:"ADMIN",children:"ADMIN"})]}));var w=Object(N.jsx)("p",{});"ADMIN"===localStorage.getItem("role")&&"ADMIN"!==Pe&&"ORG"!==Pe&&(w=Object(N.jsxs)("select",{name:Pe,onChange:f,children:[Object(N.jsx)("option",{value:""}),Object(N.jsx)("option",{value:"USER",children:"USER"}),Object(N.jsx)("option",{value:"MOD",children:"MOD"})]}));var S=Object(N.jsx)("p",{});localStorage.getItem("username")===t||x||(S=Object(N.jsx)(B.a,{variant:"contained",color:"primary",className:g.button,onClick:function(){fetch("https://amazing-office-313314.appspot.com/rest/profile/follow/"+t,{method:"POST",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:m()}).then((function(e){e.ok?window.location.reload():alert("Something went wrong")}))},children:"follow"}));var I=Object(N.jsx)("p",{});return x&&(I=Object(N.jsx)(B.a,{variant:"contained",color:"primary",onClick:function(){fetch("https://amazing-office-313314.appspot.com/rest/profile/unfollow/"+t,{method:"DELETE",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:m()}).then((function(e){e.ok?window.location.reload():(alert("Something went wrong"),console.log(e))}))},className:g.button,children:"unfollow"})),Object(N.jsxs)("div",{children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white"},children:["Username: ",t]}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white"},children:["Name: ",Ne,"\xa0",ke]}),Object(N.jsx)("br",{}),Object(N.jsxs)("span",{style:{color:"white"},children:["Role: ",Pe]}),Object(N.jsx)("br",{}),v,w,S,I,y]})},Ue=n(105),ze=n(106),Be=n(109),Fe=n(108),Le=n(69),qe={width:"80vh",height:"70vh",margin:"0 auto"},Me={float:"left",marginLeft:"310px"},Ee=0,Je=0;navigator.geolocation.getCurrentPosition((function(e){Ee=e.coords.latitude,Je=e.coords.longitude}));var Ge=function(e){Object(Be.a)(n,e);var t=Object(Fe.a)(n);function n(e){var a;return Object(Ue.a)(this,n),(a=t.call(this,e)).displayMarkers=function(){return a.state.stores.map((function(e,t){return Object(N.jsx)(Le.Marker,{id:t,position:{lat:e.latitude,lng:e.longitude},onClick:function(){return console.log("You clicked me!")}},t)}))},a.state={stores:[{lat:47.49855629475769,lng:-122.14184416996333},{latitude:47.359423,longitude:-122.021071},{latitude:47.2052192687988,longitude:-121.988426208496},{latitude:47.6307081,longitude:-122.1434325},{latitude:47.3084488,longitude:-122.2140121},{latitude:47.5524695,longitude:-122.0425407}]},a}return Object(ze.a)(n,[{key:"render",value:function(){return Object(N.jsxs)("div",{style:Me,children:[Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)("br",{}),Object(N.jsx)(Le.Map,{google:this.props.google,style:qe,initialCenter:{lat:Ee,lng:Je},zoom:14,children:this.displayMarkers()})]})}}]),n}(a.Component),He=Object(Le.GoogleApiWrapper)({apiKey:"AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM"})(Ge);function Ve(){var e,t=null!=localStorage.getItem("tokenID");return"/"===window.location.pathname&&(e=t?Object(N.jsx)(U.a,{to:"/feed"}):Object(N.jsx)(U.a,{to:"/home"})),Object(N.jsx)("div",{children:e})}function We(){return fetch("https://amazing-office-313314.appspot.com/rest/logout",{method:"POST",headers:{Accept:"application/json, text/plain","Content-Type":"application/json;charset=UTF-8"},body:JSON.stringify({tokenID:localStorage.getItem("tokenID"),username:localStorage.getItem("username")})}),localStorage.removeItem("tokenID"),localStorage.removeItem("username"),localStorage.removeItem("role"),window.location.href="../home","nop"===localStorage.getItem("hasReloaded")&&(window.location.reload(),localStorage.removeItem("hasReloaded")),Object(N.jsx)("div",{})}function _e(){var e=Object(U.f)();return De(e)}var Ke=function(){var e=null!=localStorage.getItem("tokenID");return Object(N.jsx)(D.a,{children:Object(N.jsxs)("div",{className:"App",children:[Object(N.jsx)(Ve,{}),e&&Object(N.jsx)(je,{}),!e&&Object(N.jsx)(T,{}),Object(N.jsx)(U.b,{exact:!0,path:"/home",component:$}),Object(N.jsx)(U.b,{exact:!0,path:"/register_user",component:q}),Object(N.jsx)(U.b,{exact:!0,path:"/register_organization",component:M}),Object(N.jsx)(U.b,{exact:!0,path:"/register",component:W}),Object(N.jsx)(U.b,{exact:!0,path:"/login",component:ee}),Object(N.jsx)(U.b,{exact:!0,path:"/myProfile",component:Ce}),Object(N.jsx)(U.b,{exact:!0,path:"/update",component:ce}),Object(N.jsx)(U.b,{exact:!0,path:"/delete",component:Y}),Object(N.jsx)(U.b,{exact:!0,path:"/about",component:ae}),Object(N.jsx)(U.b,{exact:!0,path:"/logout",component:We}),Object(N.jsx)(U.b,{exact:!0,path:"/password",component:Ae}),Object(N.jsx)(U.b,{exact:!0,path:"/profile/:username/",component:_e}),Object(N.jsx)(U.b,{exact:!0,path:"/Map",component:He})]})})},Ye=function(e){e&&e instanceof Function&&n.e(3).then(n.bind(null,227)).then((function(t){var n=t.getCLS,a=t.getFID,r=t.getFCP,o=t.getLCP,c=t.getTTFB;n(e),a(e),r(e),o(e),c(e)}))};c.a.render(Object(N.jsx)(r.a.StrictMode,{children:Object(N.jsx)(Ke,{})}),document.getElementById("root")),Ye()},45:function(e,t,n){}},[[171,1,2]]]);
//# sourceMappingURL=main.6fb41ccf.chunk.js.map