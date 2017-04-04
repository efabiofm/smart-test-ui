(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PruebaDialogController', PruebaDialogController);

    PruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Prueba', 'Ambiente', 'Modulo', 'Servicio', 'Metodo', 'EjecucionPrueba', 'PlanPrueba'];

    function PruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Prueba, Ambiente, Modulo, Servicio, Metodo, EjecucionPrueba, PlanPrueba) {
        var vm = this;

        vm.prueba = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ambientes = Ambiente.query();
        vm.modulos = Modulo.query();
        vm.servicios = Servicio.query();
        vm.metodos = Metodo.query();
        vm.ejecucionpruebas = EjecucionPrueba.query();
        vm.planpruebas = PlanPrueba.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.prueba.id !== null) {
                Prueba.update(vm.prueba, onSaveSuccess, onSaveError);
            } else {
                Prueba.save(vm.prueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:pruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
