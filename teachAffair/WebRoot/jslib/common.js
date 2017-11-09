//课程选择部分 开始//
var pCourse;
function courseSelect(){
	pCourse=dj.dialog({
		title : '选择所属课程',
		href : 'course!courseSelect.do',
		width : 600,
		height : 360,
		left:350,
		top:100,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveCourseAdd();
			}
		},{ 
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pCourse.dialog('close'); 
			}
		}],
		onLoad : function() {
			initCourse();
		}
	});
}
//课程选择部分 结束//

//教室选择部分 开始//
var pClassroom;
function classRoomSelect(){
	pClassroom=dj.dialog({
		title:'选择',
		href:'classroom!classroomSelect.do',
		width:500,
		height:330,
		left:350,
		top:100,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveClassroomAdd();
			}
		},{
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				pClassroom.dialog('close'); 
			}
		}],
		onLoad : function() {
			initClassroom();
		}
	});
}
//教室选择部分 结束//


//授课时间选择部分 开始//
var ptime;
function professTimeSelect(){
	ptime=dj.dialog({
		title : '选择授课时间',
		href : 'professTime!professTimeSelect.do',
		width : 600,
		height : 360,
		left:350,
		top:100,
		buttons : [ {
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				saveTimeAdd();
			}
		},{
			text: ' 取 消', 
			iconCls:'icon-cancel',
			handler: function() { 
				ptime.dialog('close'); 
			}
		}],
		onLoad : function() {
			initTime();
		}
	});
}
//授课时间选择部分 结束//