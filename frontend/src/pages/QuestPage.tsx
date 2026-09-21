import { useEffect, useState } from 'react';
import { questService } from '../services/questService';
import type { QuestAnswer } from '../types/quest';
import type { Status } from '../types/status';
import type { Difficulty } from '../types/difficulty';

export const QuestPage = () => {
  const [quest, setQuest] = useState<QuestAnswer[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [statusFilter, setStatusFilter] = useState<Status | ''>('');
  const [difficultyFilter, setDifficultyFilter] = useState<Difficulty | ''>('');

  const fetchQuests = async () => {
    try {
      setLoading(true);
      setError(null);

      const answer = await questService.getQuests(
        statusFilter || undefined,
        difficultyFilter || undefined
      );
      setQuest(answer);
    } catch (err: unknown) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('Une erreur inattendue est survenue');
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchQuests();
  }, [statusFilter, difficultyFilter]);

  return (
    <div>
      <h1>Tableau des quêtes</h1>

      <div>
        <label htmlFor="status-select">Status : </label>
        <select
          id="status-select"
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value as Status | '')}
        >
          <option value="">Tous les statuts</option>
          <option value="AVAILABLE">Disponible</option>
          <option value="ON_GOING">En cours</option>
          <option value="COMPLETED">Terminés</option>
        </select>

        <label htmlFor="difficulty-select">Difficulté : </label>
        <select
          id="difficulty-select"
          value={difficultyFilter}
          onChange={(e) => setDifficultyFilter(e.target.value as Difficulty | '')}
        >
          <option value="">Tous les niveaux</option>
          <option value="EASY">Facile</option>
          <option value="MEDIUM">Moyen</option>
          <option value="HARD">Difficile</option>
          <option value="EPIC">Epic</option>
        </select>

        {loading && <p>Chargement des quêtes en cours</p>}
        {error && <p style={{ color: 'red' }}>Erreur : {error}</p>}
        
        {!loading && !error && quest.length === 0 && (
          <p>Aucune quête ne correspond aux critères</p>
        )}

        {!loading && !error && quest.length > 0 && (
          <ul>
            {quest.map((q) => (
              <li key={q.id}>
                <strong>{q.title}</strong> — {q.difficulty} — LVL.{q.requiredLevel} — {q.goldReward}$ or — {q.xpReward}XP — {q.status} — {q.description}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};