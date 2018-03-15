(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationAuthorDetailController', SolicitationAuthorDetailController);

    SolicitationAuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SolicitationAuthor'];

    function SolicitationAuthorDetailController($scope, $rootScope, $stateParams, previousState, entity, SolicitationAuthor) {
        var vm = this;

        vm.solicitationAuthor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationAuthorUpdate', function(event, result) {
            vm.solicitationAuthor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
