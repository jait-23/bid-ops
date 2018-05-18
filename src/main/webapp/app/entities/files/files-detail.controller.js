(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('FilesDetailController', FilesDetailController);

    FilesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Files', 'Solicitations'];

    function FilesDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Files, Solicitations) {
        var vm = this;

        vm.files = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bidopscoreApp:filesUpdate', function(event, result) {
            vm.files = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
