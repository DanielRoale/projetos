<?php
// Conectar ao banco de dados (substitua com suas credenciais)
$hostname = "localhost";
$dbname = "contato";
$username = "root";
$password = "";

$conn = new mysqli($hostname, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Falha na conexão com o banco de dados: " . $conn->connect_error);
}

// Verificar se os dados do formulário foram enviados
if(isset($_POST['nome'], $_POST['email'], $_POST['mensagem'])) {
    // Obter dados do formulário
    $nome = $_POST['nome'];
    $email = $_POST['email'];
    $mensagem = $_POST['mensagem'];

    // Inserir dados no banco de dados usando instrução preparada
    $sql = "INSERT INTO novos (nome, email, mensagem) VALUES (?, ?, ?)";
    $stmt = $conn->prepare($sql);

    // Verificar se a preparação da consulta foi bem-sucedida
    if ($stmt) {
        // Vincular parâmetros
        $stmt->bind_param("sss", $nome, $email, $mensagem);

        // Executar a instrução preparada
        if ($stmt->execute()) {
            // Redirecionar para a mesma página com parâmetro de sucesso
            header("Location: index.html");
            exit();
        } else {
            echo "Erro ao enviar a mensagem: " . $stmt->error;
        }

        // Fechar a instrução preparada
        $stmt->close();
    } else {
        echo "Erro na preparação da consulta: " . $conn->error;
    }
}

// Se houver um parâmetro de sucesso na URL, exibir a mensagem
if(isset($_GET['success']) && $_GET['success'] == 'true') {
    echo "<p>Mensagem enviada com sucesso!</p>";
}

// Fechar a conexão com o banco de dados
$conn->close();
?>
