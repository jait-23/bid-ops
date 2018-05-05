(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SecondaryEvaluationDeleteController',SecondaryEvaluationDeleteController);

    SecondaryEvaluationDeleteController.$inject = ['$uibModalInstance', 'entity', 'SecondaryEvaluation'];

    function SecondaryEvaluationDeleteController($uibModalInstance, entity, SecondaryEvaluation) {
        var vm = this;

        vm.secondaryEvaluation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SecondaryEvaluation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
