(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationAuthorDeleteController',SolicitationAuthorDeleteController);

    SolicitationAuthorDeleteController.$inject = ['$uibModalInstance', 'entity', 'SolicitationAuthor'];

    function SolicitationAuthorDeleteController($uibModalInstance, entity, SolicitationAuthor) {
        var vm = this;

        vm.solicitationAuthor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SolicitationAuthor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
