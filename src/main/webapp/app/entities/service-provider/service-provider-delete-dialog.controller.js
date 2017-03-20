(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ServiceProviderDeleteController',ServiceProviderDeleteController);

    ServiceProviderDeleteController.$inject = ['$uibModalInstance', 'entity', 'ServiceProvider'];

    function ServiceProviderDeleteController($uibModalInstance, entity, ServiceProvider) {
        var vm = this;

        vm.serviceProvider = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ServiceProvider.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
