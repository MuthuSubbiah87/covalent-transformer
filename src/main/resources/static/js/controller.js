var app = angular.module('app', []);
app.controller('uploadController', function($scope, $http, $location) {
	$scope.test = "Check";
	var stompClient = null;
	$scope.showLoading = false;
	$scope.isProgresBarVisible = false;
	getFilesList();
	
	$scope.progress = 0;
	$scope.progressStyle = 'width:0%'
		
	$scope.showSuccess = false;
	connect();
	
	$scope.refreshList =  function() {
		$scope.showSuccess = false;
		getFilesList();
	};
	
	
	function connect() {
	    var socket = new SockJS('/ws-root');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	        console.log('Connected: ' + frame);
	        stompClient.subscribe('/ws-process/progress', function (msg) {
	            console.log(msg);
	            var jsonObj = JSON.parse(msg.body);
	            $scope.progress = Math.trunc((jsonObj.processed/jsonObj.total) * 100 );
	            $scope.progressStyle = 'width:' + $scope.progress + '%'
	            if ($scope.progress > 90) {
	            		$scope.refreshList();
	            		if ($scope.progress > 99) {
	            			$scope.isProgresBarVisible = false;
		            		$scope.showSuccess = true;
		            		//disconnect();
		            		$scope.progress = 0;
		            		$scope.progressStyle = 'width:0%'
	            			$scope.refreshList();
	            		}
	         	}
	            $scope.$digest();
	            return msg;
	        });
	    });
	};
	
	function disconnect() {
	    if (stompClient != null) {
	        stompClient.disconnect();
	    }
	    //setConnected(false);
	    console.log("Disconnected");
	};
	
	function getFilesList() {
		$scope.showLoading = true;
		$http({
	        method : "GET",
	        url : "/file/list"
	    }).then(function mySuccess(response) {
	    		$scope.filesList = response;
	    		console.log('file upload complete');
	    		$scope.showLoading = false;
	    }, function myError(response) {
	    		console.log('file upload error');
	    		$scope.showLoading = false;
	    });
	};
	
	$scope.setFile = function(element) {
			$scope.theFile = element.files[0];
	};

	$scope.uploadFile = function() {
		$scope.progress = 1;
		$scope.progressStyle = 'width: 1%'
		$scope.isProgresBarVisible = true;
		$scope.showSuccess = false;
		var uploadUrl = '/upload/';
		var file = $scope.theFile;
		var fd = new FormData();
		fd.append('file', file);
		console.log('file :' + file);
		console.log('uploadUrl :' + uploadUrl);
		$scope.showLoading = true;
		$http.post(uploadUrl, fd, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).then(function mySuccess(response) {
			console.log('file upload complete' + response);
			$scope.showLoading = false;
		}, function myerror(error) {
			//alert('Request for correction successfully submitted');
			$scope.showLoading = false;
		});
	};

});