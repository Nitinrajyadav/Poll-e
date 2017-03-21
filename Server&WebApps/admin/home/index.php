<?php
$active="home";
include('../includes/header.php');
?>


            <section id="content">
                <div class="container">
                    <div class="card">
                        <div class="card-header">
                            <h2>Registered Authorities<small>Vestibulum purus quam scelerisque, mollis nonummy metus</small></h2>

                            <ul class="actions">
                                <li>
                                    <a href="#">
                                        <i class="zmdi zmdi-check-all"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="zmdi zmdi-trending-up"></i>
                                    </a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" data-toggle="dropdown">
                                        <i class="zmdi zmdi-more-vert"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li>
                                            <a href="#">Change Date Range</a>
                                        </li>
                                        <li>
                                            <a href="#">Change Graph Type</a>
                                        </li>
                                        <li>
                                            <a href="#">Other Settings</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                        <div class="card-body">
                            <div class="chart-edge">
                                <div id="curved-line-chart" class="flot-chart "></div>
                            </div>
                        </div>
                    </div>

                </div>
            </section>
 <?php
include('../includes/footer.php');
?>