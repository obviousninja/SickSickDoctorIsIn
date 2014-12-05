
/*
       <div id="namesapps"></div>
    <div id="appicon"></div>
    <!---this is the website address:  https://github.com/obviousninja/SickSickDoctorIsIn/tree/master/TheDocIsIn --->
        <div id="sourcefile"><a href="TheDocIsIn.apk" download>Download link</a></div>
    <div id="apkfile"><a href="TheDocIsIn.zip" download>Download link</a></div>
    
    <div id="screenshot"></div>
    <div id="description"></div>
    <div id="test"></div>
    <div id="screencast"></div>
 
 */

$(document).ready(function(){
    $("body").css({"backgroundColor":"#CCA352"});
    $("#appicon").css({"border":"solid", "borderRadius":"10px", "top":"20px", "left":"20px", "height":"100px", "width":"100px", "position":"absolute"});
    $("#appicon").append("<img src='doctor.png' alt='doctor'>");
    $("#namesapps").css({"border":"solid", "borderRadius":"10px", "top":"20px", "left":"125px", "height": "100px", "width":"700px", "position":"absolute"});
    $("#namesapps").append("<br>Doctor is IN");
    $("#namesapps").css({"font": "italic bold 40px arial,serif", "textAlign":"center"});
    $("#sourcefile").css({"border":"solid", "borderRadius":"10px", "top":"125px", "left":"20px", "height": "100px", "width":"350px", "position":"absolute"});
    
    $("#sourcefile").append("<a href='TheDocIsIn.zip' download>SOURCE FILE DOWNLOAD</a>");
    $("#sourcefile").css({"font": "italic bold 30px arial,serif", "textAlign":"center"});    

    $("#apkfile").css({"border":"solid", "borderRadius":"10px", "top":"125px", "left":"375px", "height": "100px", "width":"450px", "position":"absolute"});
    $("#apkfile").append("<a href='TheDocIsIn.apk' download>APK DOWNLOAD</a>");
    $("#apkfile").css({"font": "italic bold 30px arial,serif", "textAlign":"center"});
    
    $("#description").css({"border":"solid", "borderRadius":"10px", "top":"230px", "left":"20px", "height": "700px", "width":"805px", "position":"absolute"});
    $("#description").append("<h3>Description</h3><b>Minimum Level SDK: </b> <span><i>11.</i></span> <b>Targeted Level SDK: </b><span><i>21.</i></span>");
    $("#description").append("<b>Development Enviroment</b> <span><i>Eclipse</i></span>");
    

    var text1 = "<div>The Doctor Is In is a question and answer app. When a user signs up, he or she</div>";
var text2 = "<div>will be granted an initial 20 coins. Asking a question costs 5 coins,</div>";
var text3 = "<div>but answering a question earns 3 coins. Multiple users can answer any question</div>";
var text4 = "<div>and anyone who views a question can vote any answer positively or negatively. The highest</div>";
var text5 = "<div>voted answers are pushed to the top of the answer list. After a user answers a question,</div>";
var text6 = "<div>the asker can decide if that answer is satisfactory. If the answer is satisfactory, the asker</div>";
var text7 = "<div>can choose that question as ''the best answer'' and close the question so no more answers can be given.</div>";
var text8 = "<div>When a user asks a question, he or she is asked what category</div>";
var text9 = "<div>they want their question to be. The home screen of our app has all of the categories as buttons and</div>";
var text10 = "<div>clicking each category button takes you to all of the questions within that category.</div>"
    $("#description").append("<p></p>",text1, text2, text3, text4, text5, text6, text7, text8, text9, text10);
    
    
    
    });