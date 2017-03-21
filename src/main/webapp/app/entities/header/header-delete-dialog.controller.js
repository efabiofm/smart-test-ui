(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HeaderDeleteController',HeaderDeleteController);

    HeaderDeleteController.$inject = ['$uibModalInstance', 'entity', 'Header'];

    function HeaderDeleteController($uibModalInstance, entity, Header) {
        var vm = this;

        vm.header = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Header.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
