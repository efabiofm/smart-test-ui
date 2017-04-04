'use strict';

describe('Controller Tests', function() {

    describe('Metodo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMetodo, MockServicio, MockPrueba, MockParametro;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMetodo = jasmine.createSpy('MockMetodo');
            MockServicio = jasmine.createSpy('MockServicio');
            MockPrueba = jasmine.createSpy('MockPrueba');
            MockParametro = jasmine.createSpy('MockParametro');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Metodo': MockMetodo,
                'Servicio': MockServicio,
                'Prueba': MockPrueba,
                'Parametro': MockParametro
            };
            createController = function() {
                $injector.get('$controller')("MetodoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:metodoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
