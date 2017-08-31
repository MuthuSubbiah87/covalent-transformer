var app = angular.module('app', []);
app.controller('uploadController', function($scope, $http, $location) {
	$scope.test = "Check";
	
	$scope.showLoading = false;
	
	getFilesList();
	
	$scope.refreshList =  function() {
		getFilesList();
	};
	
	function getFilesList() {
		$scope.showLoading = true;
		$http({
	        method : "GET",
	        url : "http://localhost:8090/file/list"
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
		var uploadUrl = 'http://localhost:8090/upload/';
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
			alert('Request for correction successfully submitted');
			$scope.showLoading = false;
		});
	};

});