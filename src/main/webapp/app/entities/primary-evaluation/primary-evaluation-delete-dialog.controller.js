(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('PrimaryEvaluationDeleteController',PrimaryEvaluationDeleteController);

    PrimaryEvaluationDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrimaryEvaluation'];

    function PrimaryEvaluationDeleteController($uibModalInstance, entity, PrimaryEvaluation) {
        var vm = this;

        vm.primaryEvaluation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrimaryEvaluation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
