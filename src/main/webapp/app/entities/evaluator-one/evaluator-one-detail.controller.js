(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('EvaluatorOneDetailController', EvaluatorOneDetailController);

    EvaluatorOneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EvaluatorOne'];

    function EvaluatorOneDetailController($scope, $rootScope, $stateParams, previousState, entity, EvaluatorOne) {
        var vm = this;

        vm.evaluatorOne = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:evaluatorOneUpdate', function(event, result) {
            vm.evaluatorOne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
