$(function() {
        
        var addAllColumnHeaders = function(myList, selector) {
            var columnSet = [];
            var headerTr$ = $('<tr/>');

            for (var i = 0 ; i < myList.length ; i++) {
                var rowHash = myList[i];
                for (var key in rowHash) {
                    if ($.inArray(key, columnSet) == -1){
                        columnSet.push(key);
                        headerTr$.append($('<th/>').html(key));
                    }
                }
            }
            $(selector).append(headerTr$);

            return columnSet;
        }
        
        var buildHtmlTable = function(myList){            
            var columns = addAllColumnHeaders(myList, "#tablearea");

            for (var i = 0 ; i < myList.length ; i++) {
                var row$ = $('<tr/>');
                for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
                    var cellValue = myList[i][columns[colIndex]];

                    if (cellValue == null) { cellValue = ""; }

                    row$.append($('<td/>').html(cellValue));
                }
                $("#tablearea").append(row$);
            }
        }

	var url = "ws://localhost:8080/week4_ca_personal/notesdisplay";
	var socket = new WebSocket(url);

	$("#sendBtnAll").on("click", function() {
		socket.send("All");
	})
        $("#sendBtnSocial").on("click", function() {
                socket.send("Social");
        })
        $("#sendBtnForSale").on("click", function() {
                socket.send("ForSale");
        })
        $("#sendBtnJobs").on("click", function() {
                socket.send("Jobs");
        })
        $("#sendBtnTution").on("click", function() {
                socket.send("Tution");
        })

	socket.onmessage = function(evt) {
		// {message: "the message" , timestamp: "time" }
		var msg = JSON.parse(evt.data);
		// $.each(msg, function(i, item){
                //    writeToChatboard(msg.timestamp + ": " + item.title);
                //});
                //writeToChatboard(msg.message);
                //$.each(JSON.parse(msg.message), function(i,note){
                //    writeToChatboard(note.title);
                //})
                $("#tablearea").children( 'tr' ).remove();
                buildHtmlTable(JSON.parse(msg.message));
	}
	socket.onopen = function() {
	}
	socket.onclose = function() {
	}

});

