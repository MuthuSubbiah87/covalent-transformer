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
	
	$scope.updateConfig =  function() {
		$scope.showLoading = true;
		$http({
	        method : "POST",
	        url : "/config/update"
	    }).then(function mySuccess(response) {
	    		$scope.updateConfigResponse = response;
	    		console.log('config update success');
	    		$scope.showLoading = false;
	    }, function myError(response) {
	    		console.log('config update failed');
	    		$scope.showLoading = false;
	    });
	};
	
	function getConfigList() {
		$scope.showLoading = true;
		$http({
	        method : "GET",
	        url : "/config/list"
	    }).then(function mySuccess(response) {
	    		$scope.getConfigResponse = response;
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