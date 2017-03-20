(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alerta', {
            parent: 'entity',
            url: '/alerta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.alerta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alerta/alertas.html',
                    controller: 'AlertaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('alerta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('alerta-detail', {
            parent: 'entity',
            url: '/alerta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.alerta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alerta/alerta-detail.html',
                    controller: 'AlertaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('alerta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Alerta', function($stateParams, Alerta) {
                    return Alerta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alerta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alerta-detail.edit', {
            parent: 'alerta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta/alerta-dialog.html',
                    controller: 'AlertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alerta', function(Alerta) {
                            return Alerta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alerta.new', {
            parent: 'alerta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta/alerta-dialog.html',
                    controller: 'AlertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mensaje: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alerta', null, { reload: 'alerta' });
                }, function() {
                    $state.go('alerta');
                });
            }]
        })
        .state('alerta.edit', {
            parent: 'alerta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta/alerta-dialog.html',
                    controller: 'AlertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alerta', function(Alerta) {
                            return Alerta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alerta', null, { reload: 'alerta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alerta.delete', {
            parent: 'alerta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta/alerta-delete-dialog.html',
                    controller: 'AlertaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Alerta', function(Alerta) {
                            return Alerta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alerta', null, { reload: 'alerta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
