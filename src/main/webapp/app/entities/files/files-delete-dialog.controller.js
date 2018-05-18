(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('FilesDeleteController',FilesDeleteController);

    FilesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Files'];

    function FilesDeleteController($uibModalInstance, entity, Files) {
        var vm = this;

        vm.files = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Files.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
